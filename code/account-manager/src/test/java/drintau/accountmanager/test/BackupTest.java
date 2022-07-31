package drintau.accountmanager.test;

import drintau.accountmanager.service.myaccount.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev_mem")
public class BackupTest {

    @Resource
    private BackupService backupService;

    @Test
    public void testBackup() {
//        backupService.backup();

    }

}
