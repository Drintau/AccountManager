package mjhct.accountmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
        logger.info("欢迎访问：{}", "http://localhost:" + port + contextPath);
    }
}
