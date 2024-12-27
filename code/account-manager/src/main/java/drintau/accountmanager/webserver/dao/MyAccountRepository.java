package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.bo.PageBO;
import drintau.accountmanager.webserver.domain.po.MyAccountPO;

import java.util.List;

public interface MyAccountRepository {

    void save(MyAccountPO po);

    void saveBatch(List<MyAccountPO> pos);

    void deleteById(Integer id);

    void update(MyAccountPO po);

    MyAccountPO getById(Integer id);

    List<MyAccountPO> list();

    List<MyAccountPO> list(PageBO pageBO);

    List<MyAccountPO> listByAppName(String appName, PageBO pageBO);

    Integer count();

    Integer countByAppName(String appName);
}
