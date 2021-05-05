package mjhct.accountmanager.service;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.domain.entity.MyAccountPO;
import mjhct.accountmanager.domain.bo.MyAccountAddBeforeBO;
import mjhct.accountmanager.domain.bo.MyAccountInfoBO;
import mjhct.accountmanager.domain.bo.MyAccountUpdateBeforeBO;
import mjhct.accountmanager.exception.BusinessException;
import mjhct.accountmanager.service.crypto.CryptoService;
import mjhct.accountmanager.util.BeanUtil;
import mjhct.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountService.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

    @Resource
    private MyAccountRepository myAccountRepository;

    public List<MyAccountInfoBO> getMyAccountByIdOrAppName(Integer id, String appName) {
        List<MyAccountInfoBO> rst = new ArrayList<>(16);
        // id优先
        if (id != null) {
            Optional<MyAccountPO> byId = myAccountRepository.findById(id);
            if (byId.isPresent()) {
                MyAccountPO myAccount = byId.get();
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
                rst.add(BeanUtil.copy(myAccount, MyAccountInfoBO.class));
            }
        } else {
            // 根据应用名称查
            List<MyAccountPO> byAppName = myAccountRepository.findByAppName(appName);
            for (MyAccountPO myAccount : byAppName) {
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
                rst.add(BeanUtil.copy(myAccount, MyAccountInfoBO.class));
            }
        }
        return rst;
    }

    @Transactional(rollbackFor = Exception.class)
    public MyAccountInfoBO addMyAccount(MyAccountAddBeforeBO myAccountAddBO) {
        MyAccountPO addAccount = BeanUtil.copy(myAccountAddBO, MyAccountPO.class);
        addAccount.setMyUsername(cryptoService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(cryptoService.encrypt(addAccount.getMyPassword()));
        OffsetDateTime nowOffsetDateTime = DateTimeUtil.nowChinaOffsetDateTime();
        addAccount.setCreateTime(nowOffsetDateTime);
        addAccount.setUpdateTime(nowOffsetDateTime);
        MyAccountPO save = myAccountRepository.save(addAccount);
        return BeanUtil.copy(save, MyAccountInfoBO.class);
    }

    public List<MyAccountInfoBO> listMyAccount(Boolean decrypt) {
        List<MyAccountPO> all = myAccountRepository.findAll();
        if (decrypt) {
            for (MyAccountPO myAccount : all) {
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            }
        }
        return BeanUtil.copyList(all, MyAccountInfoBO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public MyAccountInfoBO updateMyAccount(MyAccountUpdateBeforeBO myAccountUpdateBO) {
        Optional<MyAccountPO> old = myAccountRepository.findById(myAccountUpdateBO.getId());
        if (old.isPresent()) {
            MyAccountPO updateAccount = old.get();
            BeanUtil.copyProperties(myAccountUpdateBO, updateAccount, "id", "createTime", "updateTime");
            updateAccount.setMyUsername(cryptoService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(cryptoService.encrypt(updateAccount.getMyPassword()));
            updateAccount.setUpdateTime(DateTimeUtil.nowChinaOffsetDateTime());
            MyAccountPO updateRst = myAccountRepository.save(updateAccount);
            return BeanUtil.copy(updateRst, MyAccountInfoBO.class);
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAccount(Integer id) {
        Optional<MyAccountPO> old = myAccountRepository.findById(id);
        if (old.isPresent()) {
            myAccountRepository.deleteById(id);
            return;
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

}
