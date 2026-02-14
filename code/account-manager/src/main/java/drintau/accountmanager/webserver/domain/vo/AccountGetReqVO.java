package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountGetReqVO {

    @NotNull(message = "id不能为空")
    @JsonProperty("id")
    private Integer id;
}
