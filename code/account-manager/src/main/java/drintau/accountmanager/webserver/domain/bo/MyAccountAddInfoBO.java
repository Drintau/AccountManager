package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;

@Data
public class MyAccountAddInfoBO {

    private String appName;

    private String appUrl;

    private String myUsername;

    private String myPassword;

    private String remark;
}
