package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountListBO extends PageBO {

    /**
     * 当前页的记录
     */
    List<AccountBO> list;

    public AccountListBO(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}
