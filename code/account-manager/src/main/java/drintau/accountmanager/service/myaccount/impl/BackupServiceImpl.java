package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.dao.MyAccountBackupRepository;
import drintau.accountmanager.domain.bo.MyAccountInfoBO;
import drintau.accountmanager.domain.entity.MyAccountPO;
import drintau.accountmanager.service.myaccount.MyAccountService;
import drintau.accountmanager.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.BackupService;
import drintau.accountmanager.util.DateTimeUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@EnableScheduling
@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Resource
    private AccountManagerConfig config;

    @Resource
    private MyAccountService myAccountService;

    @Resource
    private MyAccountBackupRepository backupRepository;

    @Scheduled(cron = "0/5 * * * * ?")
    @Override
    public void startBackup() {
        log.debug("开始备份:{}", DateTimeUtil.nowChinaOffsetDateTime());
//        backupRepository.truncate();
//        List<MyAccountInfoBO> dataList = myAccountService.listMyAccount(false);
//        List<MyAccountPO> backupDataList = BeanUtil.copyList(dataList, MyAccountPO.class);
//        backupRepository.insertBatch(backupDataList);
    }
}
