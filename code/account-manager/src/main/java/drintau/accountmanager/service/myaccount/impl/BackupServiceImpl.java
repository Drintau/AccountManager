package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.dao.MyAccountRepository;
import drintau.accountmanager.domain.entity.MyAccountPO;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

@ConditionalOnProperty(prefix = "am", name = {"enable-backup"}, havingValue = "true")
@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AccountManagerConfig accountManagerConfig;

    @Autowired
    private MyAccountRepository myAccountRepository;

    @PreDestroy
    @Override
    public void backup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
        log.debug("数据路径:{}", accountManagerConfig.getFilePath());
        log.debug("备份路径:{}", accountManagerConfig.getBackupPaths());
        List<String> backupPaths = accountManagerConfig.getBackupPaths();
        if (CollectionUtils.isEmpty(backupPaths)) {
            return;
        }

        // 要备份的数据
        List<MyAccountPO> allData = myAccountRepository.list();
        if (CollectionUtils.isEmpty(allData)) {
            return;
        }
        StringBuilder insertSql1 = new StringBuilder("insert into my_account(app_name, app_url, my_username, my_password, remark, create_time, update_time) values ");
        String nullSql = "null, ";
        for (MyAccountPO data : allData) {
            insertSql1.append("(");

            if (StringUtils.hasText(data.getAppName())) {
                insertSql1.append("'").append(data.getAppName()).append("', ");
            } else {
                insertSql1.append(nullSql);
            }

            if (StringUtils.hasText(data.getAppUrl())) {
                insertSql1.append("'").append(data.getAppUrl()).append("', ");
            } else {
                insertSql1.append(nullSql);
            }

            if (StringUtils.hasText(data.getMyUsername())) {
                insertSql1.append("'").append(data.getMyUsername()).append("', ");
            } else {
                insertSql1.append(nullSql);
            }

            if (StringUtils.hasText(data.getMyPassword())) {
                insertSql1.append("'").append(data.getMyPassword()).append("', ");
            } else {
                insertSql1.append(nullSql);
            }

            if (StringUtils.hasText(data.getRemark())) {
                insertSql1.append("'").append(data.getRemark()).append("', ");
            } else {
                insertSql1.append(nullSql);
            }

            insertSql1.append("'").append(data.getCreateTime()).append("', ");
            insertSql1.append("'").append(data.getUpdateTime()).append("'),");
        }
        String insertSql = insertSql1.deleteCharAt(insertSql1.length() - 1).toString();

        // 清空数据sql
        String truncateSql = "TRUNCATE TABLE my_account RESTART IDENTITY;";

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

            for (String backupPath : backupPaths) {
                Connection connection = DriverManager.getConnection("jdbc:h2:file:" + backupPath, "root", "h2database");

                // 建立表结构
                PreparedStatement preparedStatement = connection.prepareStatement(dbInit.toString());
                preparedStatement.execute();

                // 清空表数据
                preparedStatement = connection.prepareStatement(truncateSql);
                preparedStatement.execute();

                // 写入数据
                preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.execute();

                connection.close();
            }
        } catch (Exception e) {
            log.error("备份失败", e);
        }
    }

}
