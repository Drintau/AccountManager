package drintau.accountmanager.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        AMUIContext amuiContext = AMUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = amuiContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            webServerContext.close();
            amuiContext.setWebServerContext(null);
        }
    }
}
