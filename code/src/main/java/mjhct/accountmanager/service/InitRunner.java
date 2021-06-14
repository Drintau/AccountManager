package mjhct.accountmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
@Order(1)
public class InitRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("应用启动成功。");
        try {
            // 读取数据库脚本文件
            Resource resource = resourceLoader.getResource("classpath:db/init.sql");
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder dbInit = new StringBuilder();
            String data;
            while ((data = br.readLine()) != null) {
                dbInit.append(data);
            }
            br.close();
            isr.close();
            is.close();

            // 初始化数据库
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(dbInit.toString());
            preparedStatement.execute();
            connection.close();
        } catch (Exception e) {
            logger.error("初始化数据失败！", e);
            // 抛出异常
            throw e;
        }
    }
}
