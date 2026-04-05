package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.DaemonScheduler;
import drintau.accountmanager.shared.ThreadPool;
import drintau.accountmanager.shared.util.FileUtil;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class CloseEvent implements EventHandler<WindowEvent> {

    private static final String dbFileSuffix = ".mv.db";

    @Override
    public void handle(WindowEvent windowEvent) {
        DesktopContext desktopContext = DesktopContext.getInstance();

        // 关闭服务
        ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
        if (webServerContext != null && webServerContext.isRunning()) {
            SpringApplication.exit(webServerContext);
            desktopContext.setWebServerContext(null);
        }

        LauncherContext launcherContext = LauncherContext.getInstance();
        if (BooleanUtils.isTrue(launcherContext.getEnableBackup())) {
            if (StringUtils.hasText(launcherContext.getFilePath()) && CollectionUtils.isNotEmpty(launcherContext.getBackupPaths())) {
                for (String backupPath : launcherContext.getBackupPaths()) {
                    FileUtil.copyFile(launcherContext.getFilePath() + dbFileSuffix, backupPath + dbFileSuffix);
                }
            }
        }

        ThreadPool.getInstance().shutdownNow();
        DaemonScheduler.getInstance().shutdownNow();
    }
}
