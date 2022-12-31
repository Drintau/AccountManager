package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

                // 写入数据

                connection.close();
            }
        } catch (Exception e) {
            log.error("备份失败", e);
        }
    }

}
