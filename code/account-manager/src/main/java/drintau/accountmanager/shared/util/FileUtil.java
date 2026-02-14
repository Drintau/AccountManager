package drintau.accountmanager.shared.util;

import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.shared.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUtil {

    public static void copyFile(String sourcePath, String targetPath) {
        try {
            Files.copy(Path.of(sourcePath), Path.of(targetPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("拷贝文件出错", e);
            throw new BusinessException(BusinessCode.FAIL, "拷贝文件出错");
        }
    }

}
