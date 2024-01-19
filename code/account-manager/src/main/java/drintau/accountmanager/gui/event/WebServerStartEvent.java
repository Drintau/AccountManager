package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.GUIContext;
import drintau.accountmanager.webserver.WebServerMainClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStartEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        GUIContext guiContext = GUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = guiContext.getWebServerContext();
        if (webServerContext == null || !webServerContext.isRunning()) {
            guiContext.getStartButton().setDisable(true);
            guiContext.getStopButton().setDisable(false);
            guiContext.getOpenBrowserButton().setDisable(false);
            // .headless(false) 能使用图形化界面的情况下，springboot也要用图形化模式
            SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WebServerMainClass.class).headless(false);
            webServerContext = springApplicationBuilder.run(guiContext.getArgs());
            guiContext.setWebServerContext(webServerContext);
        }
    }
}
