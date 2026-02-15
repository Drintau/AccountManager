package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            desktopContext.getStopButton().setDisable(true);
            desktopContext.getOpenBrowserButton().setDisable(true);
            desktopContext.getStartButton().setDisable(false);
            webServerContext.close();
            desktopContext.setWebServerContext(null);
        }
    }
}
