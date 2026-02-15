package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.webserver.WebServerMainClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStartEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext == null || !webServerContext.isRunning()) {
            desktopContext.getStartButton().setDisable(true);
            desktopContext.getStopButton().setDisable(false);
            desktopContext.getOpenBrowserButton().setDisable(false);
            // .headless(false) 能使用图形化界面的情况下，springboot也要用图形化模式
            SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WebServerMainClass.class).headless(false);
            webServerContext = springApplicationBuilder.run(desktopContext.getArgs());
            desktopContext.setWebServerContext(webServerContext);
        }
    }
}
