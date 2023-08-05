package drintau.accountmanager.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        AMContext amContext = AMContext.getInstance();
        ConfigurableApplicationContext webServerContext = amContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isActive()) {
            webServerContext.close();
            amContext.setWebServerContext(null);
        }
    }
}
