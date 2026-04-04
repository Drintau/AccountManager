package drintau.accountmanager.desktop.event;

import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.DaemonScheduler;
import drintau.accountmanager.shared.util.FileUtil;
import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.shared.ThreadPool;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class CloseEvent implements EventHandler<WindowEvent> {

    @Override
    public void handle(WindowEvent windowEvent) {
        ThreadPool.getInstance().shutdownNow();
        DaemonScheduler.getInstance().shutdownNow();

        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            desktopContext.getStopButton().setDisable(true);
            desktopContext.getOpenBrowserButton().setDisable(true);
            desktopContext.getStartButton().setDisable(true);
            webServerContext.close();
            desktopContext.setWebServerContext(null);
        }

        LauncherContext launcherContext = LauncherContext.getInstance();
        if (launcherContext.getEnableBackup() != null && launcherContext.getEnableBackup()) {
            if (StringUtils.hasText(launcherContext.getFilePath()) && CollectionUtils.isNotEmpty(launcherContext.getBackupPaths())) {
                for (String backupPath : launcherContext.getBackupPaths()) {
                    FileUtil.copyFile(launcherContext.getFilePath() + ".mv.db", backupPath + ".mv.db");
                }
            }
        }
    }
}
