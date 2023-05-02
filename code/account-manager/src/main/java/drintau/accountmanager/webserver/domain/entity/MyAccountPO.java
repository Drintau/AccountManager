package drintau.accountmanager.webserver.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyAccountPO {

    private Integer id;

    private String appName;

    private String appUrl;

    private String myUsername;

    private String myPassword;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
