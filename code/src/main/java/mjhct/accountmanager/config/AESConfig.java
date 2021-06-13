package mjhct.accountmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * AES加解密配置类
 */
@Component
@ConfigurationProperties(prefix = "aes")
@Validated
public class AESConfig {

    @NotEmpty(message = "请配置密钥")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "AESConfig{" +
                "key='" + key + '\'' +
                '}';
    }
}
