package mjhct.accountmanager.service;

import mjhct.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BeforeInitRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(BeforeInitRunner.class);

    @Value("${maven.version}")
    private String version;

    @Value("${maven.package-time}")
    private String packageTime;

    @Override
    public void run(ApplicationArguments args) {
        logger.info("版本号：{}", version);
        logger.info("构建时间：{}", DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(packageTime));
    }
}
