package mjhct.accountmanager.dao.impl;

import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.domain.bo.PageBO;
import mjhct.accountmanager.domain.entity.MyAccountPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyAccountRepositoryImpl implements MyAccountRepository {

    @Override
    public void save(MyAccountPO po) {

    }

    @Override
    public void batchSave(List<MyAccountPO> pos) {

    }

    @Override
    public void update(MyAccountPO po) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public MyAccountPO getById(Integer id) {
        return null;
    }

    @Override
    public List<MyAccountPO> listAll(PageBO pageBO) {
        return null;
    }

    @Override
    public List<MyAccountPO> listByAppName(String appName, PageBO pageBO) {
        return null;
    }
}
