package mjhct.accountmanager.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MyAccountQueryConditionBO extends PageBO {

    private String fuzzyName;

    private Boolean decrypt;

    public MyAccountQueryConditionBO(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}
