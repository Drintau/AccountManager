package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
    private AccountManagerConfig accountManagerConfig;

    @Override
    public void startBackup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
        log.debug("备份路径:{}", accountManagerConfig.getBackupPath());
        // 读取数据库文件
        // 写入到备份路径
    }
}
