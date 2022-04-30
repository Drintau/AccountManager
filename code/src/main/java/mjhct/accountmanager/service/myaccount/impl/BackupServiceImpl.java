package mjhct.accountmanager.service.myaccount.impl;

import lombok.extern.slf4j.Slf4j;
import mjhct.accountmanager.config.AccountManagerConfig;
import mjhct.accountmanager.service.myaccount.BackupService;
import mjhct.accountmanager.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
    private AccountManagerConfig config;

    @Override
    public void startBackup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
    }
}
