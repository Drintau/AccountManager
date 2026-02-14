package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;

@Data
public class AccountBO {

    private Integer id;

    private Integer categoryId;

    private String categoryName;

    private String appName;

    private String appUrl;

    private String username;

    private String password;

    private String remark;

    private Long createTime;

    private Long updateTime;

}
