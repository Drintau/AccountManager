package mjhct.accountmanager.service.myaccount;

import mjhct.accountmanager.domain.bo.MyAccountAddBeforeBO;
import mjhct.accountmanager.domain.bo.MyAccountInfoBO;
import mjhct.accountmanager.domain.bo.MyAccountQueryConditionBO;
import mjhct.accountmanager.domain.bo.MyAccountUpdateBeforeBO;

import java.util.List;

public interface MyAccountService {

    MyAccountInfoBO getMyAccountById(Integer id);

    /**
     * 查询所有账号信息
     * @param decrypt 是否要解密
     * @return
     */
    List<MyAccountInfoBO> listMyAccount(Boolean decrypt);

    /**
     * 根据条件查询账号集合
     * @param condition 条件集合体
     * @return
     */
    List<MyAccountInfoBO> queryMyAccount(MyAccountQueryConditionBO condition);

    MyAccountInfoBO addMyAccount(MyAccountAddBeforeBO myAccountAddBO);

    MyAccountInfoBO updateMyAccount(MyAccountUpdateBeforeBO myAccountUpdateBO);

    void deleteMyAccount(Integer id);

}
