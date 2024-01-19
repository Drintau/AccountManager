package drintau.accountmanager.gui.event;

import drintau.accountmanager.commons.util.FileUtil;
import drintau.accountmanager.gui.GUIContext;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class CloseEvent implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        GUIContext guiContext = GUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = guiContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            guiContext.getStopButton().setDisable(true);
            guiContext.getOpenBrowserButton().setDisable(true);
            guiContext.getStartButton().setDisable(true);
            webServerContext.close();
            guiContext.setWebServerContext(null);
        }
        if (guiContext.getEnableBackup() != null && guiContext.getEnableBackup()) {
            if (StringUtils.hasText(guiContext.getFilePath()) && CollectionUtils.isNotEmpty(guiContext.getBackupPaths())) {
                for (String backupPath : guiContext.getBackupPaths()) {
                    FileUtil.copyFile(guiContext.getFilePath() + ".mv.db", backupPath + ".mv.db");
                }
            }
        }
    }
}
