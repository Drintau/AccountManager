package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountAddReqVO {

    @JsonProperty("category_id")
    private Integer categoryId;

    @NotBlank(message = "应用名称不能为空")
    @Length(min = 1, max = 20, message = "应用名称长度为1-20")
    @JsonProperty("app_name")
    private String appName;

    @Length(max = 100, message = "应用网址最大长度为100")
    @JsonProperty("app_url")
    private String appUrl;

    @JsonProperty("username")
    @NotBlank(message = "账号不能为空")
    @Length(min = 1, max = 20, message = "账号长度为1-20")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "密码不能为空")
    @Length(min = 1, max = 20, message = "密码长度为1-20")
    private String password;

    @JsonProperty("remark")
    @Length(max = 100, message = "备注最大长度为100")
    private String remark;
}
