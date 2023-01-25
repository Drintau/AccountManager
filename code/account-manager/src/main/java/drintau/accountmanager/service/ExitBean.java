package drintau.accountmanager.service;

import drintau.accountmanager.service.extra.BackupService;
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
        log.info("感谢使用！程序即将退出。");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {

        }
    }
}
