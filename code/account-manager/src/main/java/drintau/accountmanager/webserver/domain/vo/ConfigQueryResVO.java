package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfigQueryResVO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("config_key")
    private String configKey;

    @JsonProperty("config_value")
    private String configValue;

    @JsonProperty("remark")
    private String remark;

}
