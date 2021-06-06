package mjhct.accountmanager.service.myaccount;

import mjhct.accountmanager.domain.bo.*;

import java.util.List;

public interface MyAccountService {

    MyAccountInfoBO getMyAccountById(Integer id);

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
    List<MyAccountInfoBO> queryMyAccount(MyAccountQueryConditionBO condition);

    MyAccountInfoBO addMyAccount(MyAccountAddBeforeBO myAccountAddBO);

    MyAccountInfoBO updateMyAccount(MyAccountUpdateBeforeBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

    void export();

}
