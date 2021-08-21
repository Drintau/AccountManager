package mjhct.accountmanager.service.myaccount;

import cn.hutool.poi.excel.ExcelWriter;
import mjhct.accountmanager.domain.bo.*;

import java.util.List;

public interface MyAccountService {

    MyAccountInfoBO getMyAccountById(Integer id);

    MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition);

    /**
     * 无条件分页查询
     * @param decrypt 是否解密
     * @param offsetPageNumber 偏移的页数，前端页码-1
     * @param pageSize 条数
     * @return
     */
    MyAccountListBO listMyAccount(Boolean decrypt, int offsetPageNumber, int pageSize);

    MyAccountInfoBO addMyAccount(MyAccountAddInfoBO myAccountAddBO);

    MyAccountInfoBO updateMyAccount(MyAccountUpdateInfoBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

    ExcelWriter export();

    void importAccounts(List<MyAccountImportAndExportInfoBO> importDataList);

}
