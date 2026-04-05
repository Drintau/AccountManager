package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.ThreadPool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.context.ConfigurableApplicationContext;

public class WebServerStartEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        synchronized (this) {
            if (!desktopContext.getStartButton().isDisabled() && (webServerContext == null || !webServerContext.isRunning())) {
                // 点击后就不能再点击
                desktopContext.getStartButton().setDisable(true);
                ThreadPool.getInstance().execute(() -> {
                    desktopContext.setWebServerContext(desktopContext.getSpringApplication().run(LauncherContext.getInstance().getArgs()));
                    desktopContext.getStopButton().setDisable(false);
                    desktopContext.getOpenBrowserButton().setDisable(false);
                });
            }
        }
    }
}
