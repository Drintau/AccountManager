package mjhct.accountmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

    @Override
    public void run(ApplicationArguments args){
        //logger.info("应用启动成功。");
        System.out.println("应用启动成功。");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(TBL_MY_ACCOUNT);
            preparedStatement.execute();
            connection.close();
        } catch (Exception e) {
            logger.error("初始化数据失败！", e);
            // 退出程序？关闭程序？
        }
    }
}
