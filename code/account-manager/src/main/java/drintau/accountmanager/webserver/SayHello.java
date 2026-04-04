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

import java.awt.*;
import java.io.IOException;
import java.net.URI;

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
        // 未使用桌面环境，才输出这两个日志；使用桌面环境时，界面上已经有显示了
        if (!launcherContext.isDesktopEnvironment()) {
            log.info("版本号：{}", launcherContext.getVersionInfo().getVersion());
            log.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(launcherContext.getVersionInfo().getBuildTime()));
        }
        log.info("欢迎使用！服务启动成功。");

        String localUrl = "http://localhost:" + port + contextPath;
        log.info("访问地址：{}", localUrl);
        log.debug("h2控制台：{}", localUrl + h2ConsolePath);

        // 使用桌面环境，传递一些配置
        if (launcherContext.isDesktopEnvironment()) {
            launcherContext.setLocalUrl(localUrl);
            launcherContext.setFilePath(webServerConfig.getFilePath());
            launcherContext.setEnableBackup(webServerConfig.getEnableBackup());
            launcherContext.setBackupPaths(webServerConfig.getBackupPaths());
        }

        // 系统桌面环境支持 且 配置了服务启动后自动访问，打开浏览器访问
        if (Desktop.isDesktopSupported() && BooleanUtils.isTrue(webServerConfig.getAutoAccessAfterStartup())) {
            URI uri = URI.create(localUrl);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (IOException e) {
                    log.warn("无法调用系统浏览器访问，请手动复制网址到浏览器访问。");
                }
            }
        }
    }
}
