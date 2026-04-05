package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.DaemonScheduler;
import drintau.accountmanager.shared.ThreadPool;
import drintau.accountmanager.shared.util.FileUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CloseEvent implements EventHandler<WindowEvent> {

    private final AtomicBoolean isClosing = new AtomicBoolean(false);

    private final String dbFileSuffix = ".mv.db";

    @Override
    public void handle(WindowEvent windowEvent) {

        // 防止重复点击 X
        if (isClosing.getAndSet(true)) {
            windowEvent.consume();
            return;
        }

        windowEvent.consume();
        DesktopContext desktopContext = DesktopContext.getInstance();
        desktopContext.getStopButton().setDisable(true);
        desktopContext.getOpenBrowserButton().setDisable(true);
        desktopContext.getStartButton().setDisable(true);

        DaemonScheduler.getInstance().submitOnceDelayTask(() -> {
            log.info("应用开始退出，请稍候。");
            // 关闭服务
            ConfigurableApplicationContext webServerContext = desktopContext.getWebServerContext();
            if (webServerContext != null && webServerContext.isRunning()) {
                SpringApplication.exit(webServerContext);
                desktopContext.setWebServerContext(null);
            }

            LauncherContext launcherContext = LauncherContext.getInstance();
            if (BooleanUtils.isTrue(launcherContext.getEnableBackup())) {
                log.info("备份功能：开始");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {}
                if (StringUtils.hasText(launcherContext.getFilePath()) && CollectionUtils.isNotEmpty(launcherContext.getBackupPaths())) {
                    for (String backupPath : launcherContext.getBackupPaths()) {
                        FileUtil.copyFile(launcherContext.getFilePath() + dbFileSuffix, backupPath + dbFileSuffix);
                    }
                }
                log.info("备份功能：完成");
            }

            ThreadPool.getInstance().shutdownNow();

            log.info("感谢使用！再见！");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException ignored) {}

            // 在调度器任务中关闭调度器就是这样操作，如果这个放在离最后一句日志很近的地方就会导致日志显示不了，应该是：1守护线程，2调度器关闭太快了
            DaemonScheduler.getInstance().shutdown();

            Platform.runLater(() -> {
                Stage stage = (Stage) windowEvent.getSource();
                stage.close();
            });

        }, 1L, TimeUnit.SECONDS);
    }
}
