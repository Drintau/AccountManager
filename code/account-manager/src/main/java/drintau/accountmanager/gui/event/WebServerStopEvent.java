package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.GUIContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        GUIContext guiContext = GUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = guiContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            guiContext.getStopButton().setDisable(true);
            guiContext.getOpenBrowserButton().setDisable(true);
            guiContext.getStartButton().setDisable(false);
            webServerContext.close();
            guiContext.setWebServerContext(null);
        }
    }
}
