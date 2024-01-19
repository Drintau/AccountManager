package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.*;

import java.util.List;

public interface MyAccountService {

    MyAccountBO getMyAccountById(Integer id);

    MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition);

    List<MyAccountBO> listMyAccount(Boolean decrypt);

    /**
     * 无条件分页查询
     * @param decrypt 是否解密
     * @param pageNumber 前端页码
     * @param pageSize 条数
     * @return
     */
    MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

    MyAccountBO addMyAccount(MyAccountBO myAccountAddBO);

    MyAccountBO updateMyAccount(MyAccountBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

    List<MyAccountImportAndExportBO> exportMyAccounts();

    void importMyAccounts(List<MyAccountImportAndExportBO> importDataList);

}
