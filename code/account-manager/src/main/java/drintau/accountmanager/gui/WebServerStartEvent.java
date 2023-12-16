package drintau.accountmanager.gui;

import drintau.accountmanager.webserver.WebServerConfiguration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStartEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        AMUIContext amuiContext = AMUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = amuiContext.getWebServerContext();
        if (webServerContext == null || !webServerContext.isRunning()) {
            amuiContext.getStartButton().setDisable(true);
            amuiContext.getStopButton().setDisable(false);
            amuiContext.getOpenBrowserButton().setDisable(false);
            SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WebServerConfiguration.class).headless(false);
            webServerContext = springApplicationBuilder.run(amuiContext.getArgs());
            amuiContext.setWebServerContext(webServerContext);
        }
    }
}
