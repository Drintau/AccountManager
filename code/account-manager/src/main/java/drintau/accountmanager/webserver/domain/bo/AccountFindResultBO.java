package drintau.accountmanager.webserver.domain.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountFindResultBO extends PageBO {

    /**
     * 当前页的记录
     */
    private List<AccountBO> list;

    public AccountFindResultBO() {
    }
    
    public AccountFindResultBO(Integer pageNum, Integer pageSize) {
        super(pageNum, pageSize);
    }
}
