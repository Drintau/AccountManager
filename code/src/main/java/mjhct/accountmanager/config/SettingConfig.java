package mjhct.accountmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 秘钥配置类
 */
@Component
@ConfigurationProperties(prefix = "am")
@Validated
public class SettingConfig {

    /**
     * aes加解密秘钥
     */
    @NotEmpty(message = "请配置密钥")
    private String securityKey;

    /**
     * 随机密码位数，默认10
     */
    private int passwordDigits = 10;

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public int getPasswordDigits() {
        return passwordDigits;
    }

    public void setPasswordDigits(int passwordDigits) {
        this.passwordDigits = passwordDigits;
    }
}
