package drintau.accountmanager.gui.event;

import drintau.accountmanager.commons.util.FileUtil;
import drintau.accountmanager.gui.AMUIContext;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class CloseEvent implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        AMUIContext amuiContext = AMUIContext.getInstance();
        ConfigurableApplicationContext webServerContext = amuiContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            amuiContext.getStopButton().setDisable(true);
            amuiContext.getOpenBrowserButton().setDisable(true);
            amuiContext.getStartButton().setDisable(true);
            webServerContext.close();
            amuiContext.setWebServerContext(null);
        }
        if (amuiContext.getEnableBackup() != null && amuiContext.getEnableBackup()) {
            if (StringUtils.hasText(amuiContext.getFilePath()) && CollectionUtils.isNotEmpty(amuiContext.getBackupPaths())) {
                for (String backupPath : amuiContext.getBackupPaths()) {
                    FileUtil.copyFile(amuiContext.getFilePath() + ".mv.db", backupPath + ".mv.db");
                }
            }
        }
    }
}
