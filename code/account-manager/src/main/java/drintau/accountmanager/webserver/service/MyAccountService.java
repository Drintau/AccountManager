package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.*;

import java.util.List;

public interface MyAccountService {

    /**
     * 根据 id 查询
     */
    MyAccountBO getMyAccountById(Integer id);

    /**
     * 根据 条件 分页查询
     */
    MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition);

    /**
     * 全量分页查询
     */
    MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

    /**
     * 新增
     */
    MyAccountBO addMyAccount(MyAccountBO myAccountAddBO);

    /**
     * 修改
     */
    MyAccountBO updateMyAccount(MyAccountBO myAccountUpdateBO);

    /**
     * 删除
     */
    void deleteMyAccount(Integer id);

    /**
     * 导出
     */
    List<MyAccountImportAndExportBO> exportMyAccounts();

    /**
     * 导入
     */
    void importMyAccounts(List<MyAccountImportAndExportBO> importDataList);

}
