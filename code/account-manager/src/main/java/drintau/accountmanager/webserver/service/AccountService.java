package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindConditionBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindResultBO;
import drintau.accountmanager.webserver.domain.bo.AccountTransferBO;

import java.util.List;

public interface AccountService {

    AccountBO getAccount(Integer id);

    AccountBO addAccount(AccountBO bo);

    AccountBO updateAccount(AccountBO bo);

    void deleteAccount(Integer id);

    AccountFindResultBO findAccount(AccountFindConditionBO conditionBO);

    void transferImport(List<AccountTransferBO> importDataList);

}
