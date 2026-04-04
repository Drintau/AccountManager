package drintau.accountmanager.launcher;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.desktop.DesktopUtil;
import drintau.accountmanager.shared.DaemonScheduler;
import drintau.accountmanager.shared.LogQueue;
import drintau.accountmanager.shared.ThreadPool;
import drintau.accountmanager.shared.util.YamlUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

/**
 * 启动器上下文，其实也是应用上下文
 */
@Getter
@Setter
public class LauncherContext {

    private LauncherContext(){}
    private static final LauncherContext instance = new LauncherContext();
    public static LauncherContext getInstance(){
        return instance;
    }

    // 启动参数
    private String[] args;

    // 是否使用useGUI参数
    private boolean useGUI;
    // 系统是否具有桌面环境支持
    private final boolean desktopSupport = Desktop.isDesktopSupported();
    // 是否桌面运行时：系统具有桌面环境支持 且 使用useGUI参数
    private boolean desktopRuntime;

    // 版本信息
    private VersionInfo versionInfo;

    // 本地访问地址，由webServer进行赋值
    private String localUrl;
    // 数据库文件路径，由webServer进行赋值
    private String filePath;
    // 是否启用备份功能，由webServer进行赋值
    private Boolean enableBackup;
    // 数据库备份文件路径，由webServer进行赋值
    private List<String> backupPaths;

    /**
     * 初始化
     */
    public void init() {
        // 版本信息，读取配置文件
        versionInfo = YamlUtil.readYamlToObj(getClass().getClassLoader().getResourceAsStream("application.yml"), VersionInfo.class);

        // 使用桌面环境必要的组件进行初始化
        if (desktopRuntime) {
            DesktopContext.getInstance();
            LogQueue.getInstance().init();
            DaemonScheduler.getInstance().init();
            ThreadPool.getInstance().init();
        }
    }

    public void fillConfig(String localUrl,
                           String filePath,
                           Boolean enableBackup,
                           List<String> backupPaths) {
        this.localUrl = localUrl;
        this.filePath = filePath;
        this.enableBackup = enableBackup;
        this.backupPaths = backupPaths;
    }

    public void submitOpenBrowserTask() {
        if (desktopSupport) {
            DesktopUtil.openBrowser(localUrl);
        }
    }

}
