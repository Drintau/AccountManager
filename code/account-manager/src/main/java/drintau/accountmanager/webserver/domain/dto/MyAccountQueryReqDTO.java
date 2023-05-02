package drintau.accountmanager.webserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountQueryReqDTO {

    // 页码和条数是必须的
    @JsonProperty("page_number")
    @NotNull(message = "请求页码必传")
    @Min(value = 1, message = "请求页码不能小于1")
    private Integer pageNumber;

    @JsonProperty("page_size")
    @NotNull(message = "每页条数必传")
    @Min(value = 1, message = "每页条数不能小于1")
    private Integer pageSize;

    /**
     * 是否解密
     */
    @JsonProperty("decrypt")
    private Boolean decrypt = false;

    /**
     * 模糊应用名称
     */
    @JsonProperty("fuzzy_name")
    private String fuzzyName;
}
