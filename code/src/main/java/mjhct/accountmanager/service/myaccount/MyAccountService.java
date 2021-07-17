package mjhct.accountmanager.service.myaccount;

import cn.hutool.poi.excel.ExcelWriter;
import mjhct.accountmanager.domain.bo.*;

public interface MyAccountService {

    MyAccountInfoBO getMyAccountById(Integer id);

    MyAccountListBO listMyAccountByAppName(Boolean decrypt, String appName, int pageNumber, int pageSize);

    /**
     *
     * @param decrypt
     * @return
     */
    /**
     * 查询所有账号信息
     * @param decrypt 是否要解密
     * @param pageNumber 起始页
     * @param pageSize 每页记录数
     * @return
     */
    MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize);

    /**
     * 根据条件查询账号集合
     * @param condition 条件集合体
     * @return
     */
    MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition);

    MyAccountInfoBO addMyAccount(MyAccountAddBeforeBO myAccountAddBO);

    MyAccountInfoBO updateMyAccount(MyAccountUpdateBeforeBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

    ExcelWriter export();

}
