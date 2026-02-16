package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountFindReqVO {

    @NotNull(message = "页码必传")
    @Min(value = 1, message = "页码不能小于1")
    @JsonProperty("page_num")
    private Integer pageNum;

    @NotNull(message = "每页记录数必传")
    @Min(value = 1, message = "每页记录数不能小于1")
    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("decrypt")
    private Boolean decrypt = false;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("keyword_app_name")
    private String keywordAppName;
}
