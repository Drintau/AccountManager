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
public class SecurityConfig {

    @NotEmpty(message = "请配置密钥")
    private String securityKey;

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    @Override
    public String toString() {
        return "SecurityConfig{" +
                "securityKey='" + securityKey + '\'' +
                '}';
    }
}
