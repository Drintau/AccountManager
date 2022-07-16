package drintau.accountmanager.service.myaccount.impl;

import com.zaxxer.hikari.HikariDataSource;
import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
    private AccountManagerConfig accountManagerConfig;

    @Resource
    private HikariDataSource dataSource;

    @Override
    public void startBackup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
        log.debug("db路径:{}", accountManagerConfig.getFilePath());
        log.debug("备份路径:{}", accountManagerConfig.getBackupPath());
        try {
            dataSource.close();
            // 读取数据库文件
            File dbFile = new File(accountManagerConfig.getFilePath() + ".mv.db");
            // 写入到备份路径
            File backupFile = new File(accountManagerConfig.getBackupPath() + ".mv.db");
            backupFile.createNewFile();
            Files.copy(dbFile.toPath(), new BufferedOutputStream(new FileOutputStream(backupFile)));
        } catch (Exception e) {
            log.error("备份失败", e);
            throw new RuntimeException(e);
        }

    }
}
