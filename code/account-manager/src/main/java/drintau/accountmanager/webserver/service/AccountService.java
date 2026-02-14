package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.AccountBO;

public interface AccountService {

    AccountBO getAccount(Integer id);

    AccountBO addAccount(AccountBO bo);

//    /**
//     * 根据 条件 分页查询
//     */
//    AccountFindResultBO queryMyAccount(AccountFindConditionBO condition);
//
//    /**
//     * 全量分页查询
//     */
//    AccountFindResultBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

//    /**
//     * 修改
//     */
//    AccountBO updateMyAccount(AccountBO myAccountUpdateBO);
//
//    /**
//     * 删除
//     */
//    void deleteMyAccount(Integer id);
//
//    /**
//     * 导出
//     */
//    List<AccountIEBO> exportMyAccounts();
//
//    /**
//     * 导入
//     */
//    void importMyAccounts(List<AccountIEBO> importDataList);

}
