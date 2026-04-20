package drintau.accountmanager.launcher;

import drintau.accountmanager.desktop.DesktopContext;
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

    private boolean webMode;
    private boolean guiMode;
    // 系统是否具有桌面环境支持
    private final boolean desktopSupport = Desktop.isDesktopSupported();
    // 是否桌面运行时：系统具有桌面环境支持 且 guiMode
    private boolean desktopRuntime;

    // 版本信息
    private VersionInfo versionInfo = YamlUtil.readYamlToObj(getClass().getClassLoader().getResourceAsStream("application.yml"), VersionInfo.class);

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
        // 桌面运行时初始化
        if (desktopRuntime) {
            DesktopContext.getInstance();
            DaemonScheduler.getInstance();
            ThreadPool.getInstance();
            LogQueue.getInstance().init();
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

}
