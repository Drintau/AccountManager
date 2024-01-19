package drintau.accountmanager.webserver.config;

import drintau.accountmanager.commons.util.SecureUtil;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.List;

/**
 * WEB服务配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "am")
@Validated
@Getter
public class WebServerConfig {

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
    private Boolean enableBackup = false;

    /**
     * 数据库备份文件路径
     */
    private List<String> backupPaths;

    /**
     * 启动后自动访问
     */
    private Boolean autoAccessAfterStartup = false;

    /**
     * 秘钥二进制字节数组
     */
    private byte[] securityKeyByteArray;

    /**
     * Bean被构造并且依赖被注入后执行初始化逻辑，把密钥转换为二进制方便后面使用
     */
    @PostConstruct
    private void initSecurityKeyByteArray() {
        securityKeyByteArray = SecureUtil.stringKeyToByteKey(securityKey);
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

    public void setAutoAccessAfterStartup(Boolean autoAccessAfterStartup) {
        this.autoAccessAfterStartup = autoAccessAfterStartup;
    }
}
