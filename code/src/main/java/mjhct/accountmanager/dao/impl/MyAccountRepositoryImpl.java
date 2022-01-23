package mjhct.accountmanager.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.dao.mapper.MyAccountMapper;
import mjhct.accountmanager.domain.bo.PageBO;
import mjhct.accountmanager.domain.entity.MyAccountPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyAccountRepositoryImpl extends ServiceImpl<MyAccountMapper, MyAccountPO> implements MyAccountRepository {

    @Override
    public List<MyAccountPO> listAll(PageBO pageBO) {
        return null;
    }

    @Override
    public List<MyAccountPO> listByAppName(String appName, PageBO pageBO) {
        return null;
    }

}
