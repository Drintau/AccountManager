package drintau.accountmanager.gui;

import drintau.accountmanager.webserver.WebServerConfiguration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@AllArgsConstructor
@Slf4j
public class WebServerStartEvent implements EventHandler<ActionEvent> {

    private String[] args;

    @Override
    public void handle(ActionEvent actionEvent) {
        AMContext amContext = AMContext.getInstance();
        ConfigurableApplicationContext webServerContext = amContext.getWebServerContext();
        if (webServerContext == null || !webServerContext.isRunning()) {
            amContext.setWebServerContext(SpringApplication.run(WebServerConfiguration.class, args));
        }
    }
}
