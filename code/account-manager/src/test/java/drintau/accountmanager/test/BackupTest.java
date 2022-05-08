package drintau.accountmanager.test;

import drintau.accountmanager.service.myaccount.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class BackupTest {

    @Resource
    private BackupService backupService;

    @Test
    public void testBackup() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("测试中...");
        }
    }

}
