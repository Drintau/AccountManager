package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.AMUIContext;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class CloseEvent implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        AMUIContext amuiContext = AMUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = amuiContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            amuiContext.getStopButton().setDisable(true);
            amuiContext.getStartButton().setDisable(true);
            webServerContext.close();
            amuiContext.setWebServerContext(null);
        }
    }
}
