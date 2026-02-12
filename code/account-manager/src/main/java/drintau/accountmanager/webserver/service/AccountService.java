package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.*;

import java.util.List;

public interface AccountService {

    /**
     * 根据 id 查询
     */
    AccountBO getMyAccountById(Integer id);

    /**
     * 根据 条件 分页查询
     */
    AccountListBO queryMyAccount(AccountFindBO condition);

    /**
     * 全量分页查询
     */
    AccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

    /**
     * 新增
     */
    AccountBO addMyAccount(AccountBO myAccountAddBO);

    /**
     * 修改
     */
    AccountBO updateMyAccount(AccountBO myAccountUpdateBO);

    /**
     * 删除
     */
    void deleteMyAccount(Integer id);

    /**
     * 导出
     */
    List<AccountIEBO> exportMyAccounts();

    /**
     * 导入
     */
    void importMyAccounts(List<AccountIEBO> importDataList);

}
