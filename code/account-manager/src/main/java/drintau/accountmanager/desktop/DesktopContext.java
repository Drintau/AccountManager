package drintau.accountmanager.desktop;

import javafx.scene.control.Button;
import lombok.Data;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * 应用GUI容器：单例
 */
@Data
public class DesktopContext {

    private DesktopContext(){}
    private static final DesktopContext instance = new DesktopContext();
    public static DesktopContext getInstance(){
        return instance;
    }

    // 启动参数
    private String[] args;

    // 版本信息
    private VersionInfo versionInfo;

    // 本地访问地址，由webServer进行赋值
    private String localUrl;

    // 数据库文件路径
    private String filePath;

    // 是否启用备份功能
    private Boolean enableBackup;

    // 数据库备份文件路径
    private List<String> backupPaths;

    // webserver实例
    private ConfigurableApplicationContext webServerContext;

    // 控件引用
    private Button startButton;
    private Button stopButton;
    private Button openBrowserButton;

}
