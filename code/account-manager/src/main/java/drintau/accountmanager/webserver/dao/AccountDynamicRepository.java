package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.bo.AccountFindConditionBO;
import drintau.accountmanager.webserver.domain.po.AccountPO;

import java.util.List;

public interface AccountDynamicRepository {

    List<AccountPO> findByCondition(AccountFindConditionBO conditionBO);

    Integer countByCondition(AccountFindConditionBO conditionBO);

}
