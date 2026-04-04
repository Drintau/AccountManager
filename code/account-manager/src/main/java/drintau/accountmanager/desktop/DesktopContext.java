package drintau.accountmanager.desktop;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用GUI容器：单例
 */
@Data
public class DesktopContext {

    private DesktopContext(){}
    private static class InitDesktopContext {
        private static final DesktopContext INSTANCE = new DesktopContext();
    }
    public static DesktopContext getInstance(){
        return InitDesktopContext.INSTANCE;
    }

    // webserver实例
    private SpringApplication springApplication;
    private ConfigurableApplicationContext webServerContext;

    // 控件引用
    private Button startButton;
    private Button stopButton;
    private Button openBrowserButton;
    private TextArea textArea;

}
