package drintau.accountmanager.domain.bo;

import lombok.Data;

@Data
public class MyAccountUpdateInfoBO {

    private Integer id;

    private String appName;

    private String appUrl;

    private String myUsername;

    private String myPassword;

    private String remark;
}
