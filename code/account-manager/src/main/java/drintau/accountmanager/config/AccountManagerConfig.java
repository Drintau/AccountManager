package drintau.accountmanager.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.List;

/**
 * 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "am")
@Validated
@Getter
public class AccountManagerConfig {

    /**
     * 加解密秘钥
     */
    @NotBlank(message = "请配置密钥")
    private String securityKey;

    /**
     * 数据库文件路径
     */
    @NotBlank(message = "请配置文件存储路径")
    private String filePath;

    /**
     * 随机密码位数，默认10
     */
    private int passwordDigits = 10;

    /**
     * 是否启用备份功能
     */
    private Boolean enableBackup;

    /**
     * 数据库备份文件路径
     */
    private List<String> backupPaths;

    /**
     * 秘钥二进制字节数组
     */
    private byte[] securityKeyByteArray;

    @PostConstruct
    private void initSecurityKeyByteArray() {
        securityKeyByteArray = Base64.getDecoder().decode(securityKey);
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setPasswordDigits(int passwordDigits) {
        this.passwordDigits = passwordDigits;
    }

    public void setEnableBackup(Boolean enableBackup) {
        this.enableBackup = enableBackup;
    }

    public void setBackupPaths(List<String> backupPaths) {
        this.backupPaths = backupPaths;
    }
}
