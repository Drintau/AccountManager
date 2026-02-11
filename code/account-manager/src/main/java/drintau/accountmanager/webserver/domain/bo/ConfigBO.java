package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;

@Data
public class ConfigBO {

    private Integer id;

    private String configKey;

    private String configValue;

    private String remark;

}
