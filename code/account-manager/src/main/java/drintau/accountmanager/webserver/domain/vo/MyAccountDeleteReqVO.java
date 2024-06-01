package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountDeleteReqVO {

    @JsonProperty("id")
    @NotNull(message = "要删除的账号id不能为空")
    private Integer id;
}
