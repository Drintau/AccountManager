package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Override
    public void startBackup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
    }
}
