package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.AMUIContext;
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
            // .headless(false) 能使用图形化界面的情况下，springboot也要用图形化模式
            SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WebServerConfiguration.class).headless(false);
            webServerContext = springApplicationBuilder.run(amuiContext.getArgs());
            amuiContext.setWebServerContext(webServerContext);
        }
    }
}
