package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountAddReqVO {

    @JsonProperty("name")
    @NotEmpty(message = "应用名称不能为空")
    @Length(min = 1, max = 20, message = "应用名称长度为1-20")
    private String appName;

    @JsonProperty("url")
    @Length(max = 100, message = "应用网址最大长度为100")
    private String appUrl;

    @JsonProperty("username")
    @NotEmpty(message = "账号不能为空")
    @Length(min = 1, max = 20, message = "账号长度为1-20")
    private String myUsername;

    @JsonProperty("password")
    @NotEmpty(message = "密码不能为空")
//    @Pattern(regexp = "^(?![a-z0-9]{6,12})(?![0-9A-Z]{6,12})(?![a-zA-Z]{6,12})[a-zA-Z0-9].{5,11}$",
//             message = "密码长度6-12位，不能以特殊符号开头，且前6位不能仅有：小写字母+数字、大写字母+数字、大小写字母")
    @Length(min = 1, max = 20, message = "密码长度为1-20")
    private String myPassword;

    @JsonProperty("remark")
    @Length(max = 200, message = "备注最大长度为200")
    private String remark;
}
