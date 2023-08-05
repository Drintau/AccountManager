package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import drintau.accountmanager.commons.util.DateTimeUtil;

import java.time.LocalDateTime;

@Data
public class MyAccountQueryResVO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String appName;

    @JsonProperty("url")
    private String appUrl;

    @JsonProperty("username")
    private String myUsername;

    @JsonProperty("password")
    private String myPassword;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("create_time")
    @JsonFormat(pattern = DateTimeUtil.DATETIME_PATTERN_Y_M_D_H_M_S)
    private LocalDateTime createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern = DateTimeUtil.DATETIME_PATTERN_Y_M_D_H_M_S)
    private LocalDateTime updateTime;

}
