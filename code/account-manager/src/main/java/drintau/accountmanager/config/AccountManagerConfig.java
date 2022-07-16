package drintau.accountmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.List;

/**
 * 秘钥配置类
 */
@Configuration
@ConfigurationProperties(prefix = "am")
@Validated
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
     * 数据库备份文件路径
     */
    private List<String> backupPaths;

    /**
     * 随机密码位数，默认10
     */
    private int passwordDigits = 10;

    /**
     * 秘钥二进制字节数组
     */
    private byte[] securityKeyByteArray;

    @PostConstruct
    private void initSecurityKeyByteArray() {
        securityKeyByteArray = Base64.getDecoder().decode(securityKey);
    }

    public byte[] getSecurityKeyByteArray() {
        return securityKeyByteArray;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getBackupPaths() {
        return backupPaths;
    }

    public void setBackupPaths(List<String> backupPaths) {
        this.backupPaths = backupPaths;
    }

    public int getPasswordDigits() {
        return passwordDigits;
    }

    public void setPasswordDigits(int passwordDigits) {
        this.passwordDigits = passwordDigits;
    }
}
