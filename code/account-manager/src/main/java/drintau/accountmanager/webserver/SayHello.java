package drintau.accountmanager.webserver;

import drintau.accountmanager.commons.util.DateTimeUtil;
import drintau.accountmanager.gui.GUIContext;
import drintau.accountmanager.webserver.config.WebServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Component
@Order(1)
@Slf4j
public class SayHello implements ApplicationRunner {

    @Value("${maven.version:}")
    private String version;

    @Value("${maven.package-time:}")
    private String packageTime;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Resource
    private WebServerConfig webServerConfig;

    @Override
    public void run(ApplicationArguments args) {
        log.info("版本号：{}", version);
        log.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(packageTime));
        log.info("服务启动成功。");

        String localUrl = "http://localhost:" + port + contextPath;
        log.info("欢迎访问：{}", localUrl);
        log.debug("h2控制台地址：{}", localUrl + h2ConsolePath);

        // 有GUI的环境
        if (Desktop.isDesktopSupported()) {
            GUIContext guiContext = GUIContext.getInstance();
            guiContext.setLocalUrl(localUrl);
            guiContext.setFilePath(webServerConfig.getFilePath());
            guiContext.setEnableBackup(webServerConfig.getEnableBackup());
            guiContext.setBackupPaths(webServerConfig.getBackupPaths());
            // 配置了 服务启动后自动访问
            if (webServerConfig.getAutoAccessAfterStartup() != null && webServerConfig.getAutoAccessAfterStartup()) {
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
}
