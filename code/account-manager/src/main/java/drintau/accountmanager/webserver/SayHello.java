package drintau.accountmanager.webserver;

import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void run(ApplicationArguments args) {
        log.info("版本号：{}", version);
        log.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(packageTime));
        log.info("应用启动成功。");

        String localUrl = "http://localhost:" + port + contextPath;
        log.info("欢迎访问：{}", localUrl);
        log.debug("h2控制台地址：{}", localUrl + h2ConsolePath);

        // 如果支持调用系统浏览器打开网址，则进行此操作
        if (Desktop.isDesktopSupported()) {
            URI uri = URI.create(localUrl);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (IOException e) {
                    log.warn("无法调用系统浏览器打开网址，请手动复制网址到浏览器打开。");
                }
            }
        }
    }
}
