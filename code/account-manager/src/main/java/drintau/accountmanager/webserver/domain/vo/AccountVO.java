package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountVO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("app_url")
    private String appUrl;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("remark")
    private String remark;

//    @JsonProperty("create_time")
//    @JsonFormat(pattern = DateTimeUtil.DATETIME_PATTERN_Y_M_D_H_M_S)
//    private LocalDateTime createTime;
//
//    @JsonProperty("update_time")
//    @JsonFormat(pattern = DateTimeUtil.DATETIME_PATTERN_Y_M_D_H_M_S)
//    private LocalDateTime updateTime;

}
