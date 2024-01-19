package drintau.accountmanager.commons.util;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.exception.CommonException;
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
            throw new CommonException(CommonCode.SYSTEM_ERROR, "拷贝文件出错");
        }
    }

}
