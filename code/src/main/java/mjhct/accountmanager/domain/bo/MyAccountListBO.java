package mjhct.accountmanager.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyAccountListBO extends PageBO{

    /**
     * 当前页的记录
     */
    List<MyAccountInfoBO> list;

    public MyAccountListBO(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}
