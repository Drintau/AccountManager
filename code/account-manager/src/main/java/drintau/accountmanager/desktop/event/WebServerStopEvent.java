package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.shared.util.ThreadPoolUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStopEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            desktopContext.getStopButton().setDisable(true);
            desktopContext.getOpenBrowserButton().setDisable(true);
            ThreadPoolUtil.execute(() -> {
                SpringApplication.exit(webServerContext);
                desktopContext.setWebServerContext(null);
                desktopContext.getStartButton().setDisable(false);
            });
        }
    }
}
