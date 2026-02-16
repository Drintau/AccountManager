package drintau.accountmanager.webserver.domain.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountFindConditionBO extends PageBO {

    private Boolean decrypt;

    private Integer categoryId;

    private String keywordAppName;

    public AccountFindConditionBO() {
    }

    public AccountFindConditionBO(Integer pageNum, Integer pageSize) {
        super(pageNum, pageSize);
    }

}
