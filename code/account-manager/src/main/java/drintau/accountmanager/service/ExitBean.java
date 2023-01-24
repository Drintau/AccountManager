package drintau.accountmanager.service;

import drintau.accountmanager.service.myaccount.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExitBean implements DisposableBean {

    @Autowired(required = false)
    private BackupService backupService;

    @Override
    public void destroy() {
        if (backupService != null) {
            backupService.backup();
        }
        log.info("应用程序即将退出");
    }
}
