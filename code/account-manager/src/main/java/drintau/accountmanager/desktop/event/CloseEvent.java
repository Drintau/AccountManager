package drintau.accountmanager.desktop.event;

import drintau.accountmanager.shared.util.FileUtil;
import drintau.accountmanager.desktop.DesktopContext;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class CloseEvent implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            desktopContext.getStopButton().setDisable(true);
            desktopContext.getOpenBrowserButton().setDisable(true);
            desktopContext.getStartButton().setDisable(true);
            webServerContext.close();
            desktopContext.setWebServerContext(null);
        }
        if (desktopContext.getEnableBackup() != null && desktopContext.getEnableBackup()) {
            if (StringUtils.hasText(desktopContext.getFilePath()) && CollectionUtils.isNotEmpty(desktopContext.getBackupPaths())) {
                for (String backupPath : desktopContext.getBackupPaths()) {
                    FileUtil.copyFile(desktopContext.getFilePath() + ".mv.db", backupPath + ".mv.db");
                }
            }
        }
    }
}
