package drintau.accountmanager.webserver.config;

import drintau.accountmanager.shared.util.SecureUtil;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

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
    @Setter
    private String securityKey;

    /**
     * 数据库文件路径
     */
    @NotBlank(message = "请配置文件存储路径")
    @Setter
    private String filePath;

    /**
     * 是否启用备份功能
     */
    @Setter
    private Boolean enableBackup = false;

    /**
     * 数据库备份文件路径
     */
    @Setter
    private List<String> backupPaths;

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

}
