package mjhct.accountmanager.service.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DataBaseInitService {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseInitService.class);

    private static final String TBL_MY_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS my_account(\n" +
                    "    id INT PRIMARY KEY COMMENT '逻辑主键',\n" +
                    "    app_name VARCHAR(20) COMMENT '应用名称',\n" +
                    "    url VARCHAR(100) COMMENT '网址',\n" +
                    "    my_username VARCHAR(200) COMMENT '加密用户名',\n" +
                    "    my_password VARCHAR(200) COMMENT '加密密码',\n" +
                    "    remark VARCHAR(200) COMMENT '备注',\n" +
                    "    create_time TIMESTAMP(3) WITH TIME ZONE COMMENT '创建时间',\n" +
                    "    update_time TIMESTAMP(3) WITH TIME ZONE COMMENT '更新时间'\n" +
                    ")";

    @Resource
    private DataSource dataSource;

    public void initTable() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(TBL_MY_ACCOUNT);
        connection.close();
    }

}
