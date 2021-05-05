package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import mjhct.accountmanager.util.DateTimeUtil;

import java.time.OffsetDateTime;

public class MyAccountAddResDTO {

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
    private OffsetDateTime createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern = DateTimeUtil.DATETIME_PATTERN_Y_M_D_H_M_S)
    private OffsetDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
        this.updateTime = updateTime;
    }

}
