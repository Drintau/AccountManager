package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfigUpdateReqVO {

    @NotBlank(message = "配置项不能为空")
    @JsonProperty("config_key")
    private String configKey;

    @JsonProperty("config_value")
    private String configValue;

}
