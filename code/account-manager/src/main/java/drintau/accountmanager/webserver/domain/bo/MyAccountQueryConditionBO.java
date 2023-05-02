package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyAccountQueryConditionBO extends PageBO {

    private String fuzzyName;

    private Boolean decrypt;

}
