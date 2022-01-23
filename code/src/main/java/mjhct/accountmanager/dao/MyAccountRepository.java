package mjhct.accountmanager.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import mjhct.accountmanager.domain.bo.PageBO;
import mjhct.accountmanager.domain.entity.MyAccountPO;

import java.util.List;

public interface MyAccountRepository extends IService<MyAccountPO> {

    List<MyAccountPO> listAll(PageBO pageBO);

    List<MyAccountPO> listByAppName(String appName, PageBO pageBO);

}
