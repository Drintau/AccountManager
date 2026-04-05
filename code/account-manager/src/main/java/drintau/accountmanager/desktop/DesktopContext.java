package drintau.accountmanager.desktop;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Setter
@Getter
public class DesktopContext {

    private DesktopContext(){}
    private static class InitDesktopContext {
        private static final DesktopContext INSTANCE = new DesktopContext();
    }
    public static DesktopContext getInstance(){
        return InitDesktopContext.INSTANCE;
    }

    // webserver
    private SpringApplication springApplication;
    private ConfigurableApplicationContext webServerContext;

    // 控件引用
    private Button startButton;
    private Button stopButton;
    private Button openBrowserButton;
    private TextArea textArea;

}
