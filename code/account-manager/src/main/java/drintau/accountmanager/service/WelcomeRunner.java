package drintau.accountmanager.service;

import drintau.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Component
@Order(1)
public class WelcomeRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeRunner.class);

    @Value("${maven.version}")
    private String version;

    @Value("${maven.package-time}")
    private String packageTime;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Override
    public void run(ApplicationArguments args) {
        logger.info("版本号：{}", version);
        logger.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(packageTime));
        logger.info("应用启动成功。");

        String localUrl = "http://localhost:" + port + contextPath;
        logger.info("欢迎访问：{}", localUrl);
        logger.debug("h2控制台地址：{}", localUrl + h2ConsolePath);

        // 如果支持调用系统浏览器打开网址，则进行此操作
        if (Desktop.isDesktopSupported()) {
            URI uri = URI.create(localUrl);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (IOException e) {
                    logger.warn("无法调用系统浏览器打开网址，请手动复制网址到浏览器打开。");
                }
            }
        }
    }
}
