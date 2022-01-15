package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountDeleteReqDTO {

    @JsonProperty("id")
    @NotNull(message = "要删除的账号id不能为空")
    private Integer id;
}
