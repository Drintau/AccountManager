package mjhct.accountmanager.service;

import cn.hutool.core.util.RandomUtil;
import mjhct.accountmanager.bo.MyAccountAddBO;
import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.entity.MyAccount;
import mjhct.accountmanager.service.crypto.CryptoService;
import mjhct.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class MyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountService.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

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

    public MyAccount addMyAccount(MyAccountAddBO myAccountAddBO) {
        MyAccount addAccount = new MyAccount();
        BeanUtils.copyProperties(myAccountAddBO, addAccount);
        addAccount.setMyUsername(cryptoService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(cryptoService.encrypt(addAccount.getMyPassword()));
        OffsetDateTime nowOffsetDateTime = DateTimeUtil.nowOffsetDateTime();
        addAccount.setCreateTime(nowOffsetDateTime);
        addAccount.setUpdateTime(nowOffsetDateTime);
        logger.info("添加到数据库的数据是:{}", addAccount);
        addAccount.setId(RandomUtil.randomInt());
        logger.info("添加到数据库的结果是{}", addAccount);
        return addAccount;
    }

    public Iterable<MyAccount> listMyAccount() {
        Iterable<MyAccount> all = myAccountRepository.findAll();
        return all;
    }

}
