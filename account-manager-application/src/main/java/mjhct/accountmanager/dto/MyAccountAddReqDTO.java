package mjhct.accountmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountAddReqDTO {

    @JsonProperty("name")
    @NotEmpty(message = "应用名称不能为空")
    @Length(min = 1, max = 20, message = "应用名称长度为1-20")
    private String appName;

    @JsonProperty("url")
    @Length(min = 1, max = 100, message = "应用网址长度为1-100")
    private String appUrl;

    @JsonProperty("username")
    @NotEmpty(message = "账号不能为空")
    @Length(min = 1, max = 20, message = "账号长度为1-20")
    private String myUsername;

    @JsonProperty("password")
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^(?![a-z0-9]{6,12})(?![0-9A-Z]{6,12})(?![a-zA-Z]{6,12})[a-zA-Z0-9].{5,11}$",
             message = "密码长度6-12位，不能以特殊符号开头，且前6位不能仅有：小写字母+数字、大写字母+数字、大小写字母")
    private String myPassword;

    @JsonProperty("remark")
    @Length(min = 1, max = 200, message = "说明长度为1-200")
    private String remark;

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

    @Override
    public String toString() {
        return "MyAccountAddReqDTO{" +
                "appName='" + appName + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", myUsername='" + myUsername + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
