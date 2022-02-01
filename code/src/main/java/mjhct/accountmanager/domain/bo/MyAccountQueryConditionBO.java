package mjhct.accountmanager.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyAccountQueryConditionBO extends PageBO {

    private String fuzzyName;

    private Boolean decrypt;

}
