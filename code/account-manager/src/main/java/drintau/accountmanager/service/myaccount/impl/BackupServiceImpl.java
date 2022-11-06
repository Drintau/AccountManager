package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

@ConditionalOnProperty(prefix = "am", name = {"enable-backup"}, havingValue = "true")
@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
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

    }
}
