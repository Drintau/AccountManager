package drintau.accountmanager.webserver.service.myaccount;

import drintau.accountmanager.webserver.domain.bo.*;

import java.util.List;

public interface MyAccountService {

    MyAccountInfoBO getMyAccountById(Integer id);

    MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition);

    List<MyAccountInfoBO> listMyAccount(Boolean decrypt);

    /**
     * 无条件分页查询
     * @param decrypt 是否解密
     * @param pageNumber 前端页码
     * @param pageSize 条数
     * @return
     */
    MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

    MyAccountInfoBO addMyAccount(MyAccountAddInfoBO myAccountAddBO);

    MyAccountInfoBO updateMyAccount(MyAccountUpdateInfoBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

    List<MyAccountImportAndExportInfoBO> exportAccounts();

    void importAccounts(List<MyAccountImportAndExportInfoBO> importDataList);

}
