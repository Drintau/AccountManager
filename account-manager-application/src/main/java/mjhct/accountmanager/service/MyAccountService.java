package mjhct.accountmanager.service;

import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.entity.MyAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class MyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountService.class);

    @Resource
    private MyAccountRepository myAccountRepository;

    public MyAccount getMyAccountById(Integer id) {
        Optional<MyAccount> byId = myAccountRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        logger.warn("未找到id为{}的数据", id);
        return null;
    }

    public MyAccount saveMyAccount(MyAccount myAccount) {
        MyAccount save = myAccountRepository.save(myAccount);
        return save;
    }

    public Iterable<MyAccount> listMyAccount() {
        Iterable<MyAccount> all = myAccountRepository.findAll();
        return all;
    }

}
