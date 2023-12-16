package drintau.accountmanager.gui;

import drintau.accountmanager.gui.domain.ConfigValue;
import javafx.scene.control.Button;
import lombok.Data;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用GUI容器：单例
 */
@Data
public class AMUIContext {

    private AMUIContext(){}
    private static final AMUIContext instance = new AMUIContext();
    public static AMUIContext getInstance(){
        return instance;
    }

    // 启动参数
    private String[] args;

    // 从配置文件读取的需要用到的参数
    private ConfigValue configValue;

    // 本地访问地址，由webServer进行时赋值
    private String localUrl;

    // webserver实例
    private ConfigurableApplicationContext webServerContext;

    // 控件引用
    private Button startButton;
    private Button stopButton;
    private Button openBrowserButton;

}
