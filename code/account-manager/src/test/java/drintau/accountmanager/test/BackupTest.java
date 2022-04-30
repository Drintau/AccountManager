package drintau.accountmanager.test;

import drintau.accountmanager.service.myaccount.BackupService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BackupTest {

    @Resource
    private BackupService backupService;

    @Test
    public void testBackup() {
        backupService.startBackup();
    }

}
