package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.shared.ThreadPool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        synchronized (this) {
            if (!desktopContext.getStopButton().isDisabled() && webServerContext != null && webServerContext.isRunning()) {
                desktopContext.getStopButton().setDisable(true);
                desktopContext.getOpenBrowserButton().setDisable(true);
                ThreadPool.getInstance().execute(() -> {
                    SpringApplication.exit(webServerContext);
                    desktopContext.setWebServerContext(null);
                    desktopContext.getStartButton().setDisable(false);
                });
            }
        }
    }
}
