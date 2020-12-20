package mjhct.accountmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class InitService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitService.class);

    private static final String TBL_MY_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS my_account(\n" +
            "    id INT IDENTITY COMMENT '逻辑主键',\n" +
            "    app_name VARCHAR(20) NOT NULL COMMENT '应用名称',\n" +
            "    app_url VARCHAR(100) COMMENT '网址',\n" +
            "    my_username VARCHAR(200) NOT NULL COMMENT '加密用户名',\n" +
            "    my_password VARCHAR(200) NOT NULL COMMENT '加密密码',\n" +
            "    remark VARCHAR(200) COMMENT '备注',\n" +
            "    create_time TIMESTAMP(3) WITH TIME ZONE COMMENT '创建时间',\n" +
            "    update_time TIMESTAMP(3) WITH TIME ZONE COMMENT '更新时间'\n" +
            ")";

    @Resource
    private DataSource dataSource;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Override
    public void run(ApplicationArguments args){
        logger.info("应用启动成功，即将执行初始化数据库操作。");
        try {
            String[] split = dataSourceProperties.getUrl().split("/");
            String dbName = split[1];
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), dbName, "my_account", new String[]{"TABLE"});
            if (tables == null) {
                logger.info("数据库没有必要数据，执行初始化。");
                PreparedStatement preparedStatement = connection.prepareStatement(TBL_MY_ACCOUNT);
                preparedStatement.execute();
                connection.close();
            } else {
                logger.info("数据库已有必要数据，无需初始化。");
            }
        } catch (Exception e) {
            logger.error("初始化数据库失败！", e);
            // 退出程序？关闭程序？
        }
    }
}
