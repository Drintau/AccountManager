package drintau.accountmanager.webserver;

import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.util.DateTimeUtil;
import drintau.accountmanager.webserver.config.WebServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(1)
@Slf4j
public class SayHello implements ApplicationRunner {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    private final WebServerConfig webServerConfig;

    @Override
    public void run(ApplicationArguments args) {
        LauncherContext launcherContext = LauncherContext.getInstance();
        // 非桌面运行时，才输出这两个日志；桌面运行时，界面上已经有显示了
        if (!launcherContext.isDesktopRuntime()) {
            log.info("版本号：{}", launcherContext.getVersionInfo().getVersion());
            log.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(launcherContext.getVersionInfo().getBuildTime()));
        }
        log.info("欢迎使用！服务启动成功。");

        String localUrl = "http://localhost:" + port + contextPath;
        log.info("访问地址：{}", localUrl);
        log.debug("h2控制台：{}", localUrl + h2ConsolePath);

        // 桌面运行时，传递参数配置
        if (launcherContext.isDesktopRuntime()) {
            launcherContext.fillConfig(localUrl, webServerConfig.getFilePath(), webServerConfig.getEnableBackup(), webServerConfig.getBackupPaths());
        }

        // 配置了服务启动后自动访问，提交一个打开浏览器的任务
        if (BooleanUtils.isTrue(webServerConfig.getAutoAccessAfterStartup())) {
            log.info("因配置了启动后自动访问，即将打开浏览器访问");
            launcherContext.submitOpenBrowserTask();
        }
    }
}
