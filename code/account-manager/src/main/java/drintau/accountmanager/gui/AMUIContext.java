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

    // 配置参数
    private ConfigValue configValue;

    // webserver实例
    private ConfigurableApplicationContext webServerContext;

    // 控件引用
    private Button startButton;
    private Button stopButton;
    private Button openBrowserButton;

}
