package mjhct.accountmanager.service;

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
@Order(2)
public class AfterInitCompletedRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AfterInitCompletedRunner.class);

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void run(ApplicationArguments args) {
        String localUrl = "http://localhost:" + port + contextPath;
        logger.info("欢迎访问：{}", localUrl);

        // 如果支持调用系统浏览器打开网址，则进行此操作
        if (Desktop.isDesktopSupported()) {
            URI uri = URI.create(localUrl);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (IOException e) {
                    logger.info("无法调用系统浏览器打开网址，请手动复制网址到浏览器打开。");
                }
            }
        }
    }
}
