package mjhct.accountmanager.dao;

import mjhct.accountmanager.domain.bo.PageBO;
import mjhct.accountmanager.domain.entity.MyAccountPO;

import java.util.List;

public interface MyAccountRepository {

    void save(MyAccountPO po);

    void batchSave(List<MyAccountPO> pos);

    void update(MyAccountPO po);

    void deleteById(Integer id);

    MyAccountPO getById(Integer id);

    List<MyAccountPO> listAll(PageBO pageBO);

    List<MyAccountPO> listByAppName(String appName, PageBO pageBO);

}
