package drintau.accountmanager.service.myaccount.impl;

import com.zaxxer.hikari.HikariDataSource;
import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
    private AccountManagerConfig accountManagerConfig;

    @Resource
    private HikariDataSource dataSource;

    @Override
    public void backup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
        log.debug("db路径:{}", accountManagerConfig.getFilePath());
        log.debug("备份路径:{}", accountManagerConfig.getBackupPaths());
        List<String> backupPaths = accountManagerConfig.getBackupPaths();
        if (CollectionUtils.isEmpty(backupPaths)) {
            return;
        }
        dataSource.close();
        // 读取数据库文件
        File dbFile = new File(accountManagerConfig.getFilePath() + ".mv.db");
        for (String backupPath : backupPaths) {
            try {
                // 写入到备份路径
                File backupFile = new File(backupPath + ".mv.db");
                backupFile.createNewFile();
                Files.copy(dbFile.toPath(), new BufferedOutputStream(new FileOutputStream(backupFile)));
            } catch (Exception e) {
                log.error("路径 {} 备份失败", backupPath, e);
            }
        }

    }
}
