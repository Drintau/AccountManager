package mjhct.accountmanager.service;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.entity.bo.MyAccountAddBO;
import mjhct.accountmanager.entity.bo.MyAccountUpdateBO;
import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.entity.dto.MyAccountQueryResDTO;
import mjhct.accountmanager.entity.MyAccount;
import mjhct.accountmanager.exception.BusinessException;
import mjhct.accountmanager.service.crypto.CryptoService;
import mjhct.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    public List<MyAccountQueryResDTO> getMyAccountByIdOrAppName(Integer id, String appName) {
        // id优先
        if (id != null) {

            Optional<MyAccount> byId = myAccountRepository.findById(id);
            List<MyAccountQueryResDTO> rst = new ArrayList<>(2);
            if (byId.isPresent()) {
                MyAccount myAccount = byId.get();
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
                MyAccountQueryResDTO resDTO = myAccountEntityToResDTO(myAccount);
                rst.add(resDTO);
            }
            return rst;
        }

        // 根据应用名称查
        List<MyAccount> byAppName = myAccountRepository.findByAppName(appName);
        List<MyAccountQueryResDTO> rst = new ArrayList<>(byAppName.size()*2);
        for (MyAccount myAccount : byAppName) {
            myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            MyAccountQueryResDTO resDTO = myAccountEntityToResDTO(myAccount);
            rst.add(resDTO);
        }
        return rst;
    }

    @Transactional
    public MyAccount addMyAccount(MyAccountAddBO myAccountAddBO) {
        MyAccount addAccount = new MyAccount();
        BeanUtils.copyProperties(myAccountAddBO, addAccount);
        addAccount.setMyUsername(cryptoService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(cryptoService.encrypt(addAccount.getMyPassword()));
        OffsetDateTime nowOffsetDateTime = DateTimeUtil.nowChinaOffsetDateTime();
        addAccount.setCreateTime(nowOffsetDateTime);
        addAccount.setUpdateTime(nowOffsetDateTime);
        logger.debug("添加到数据库的数据是:{}", addAccount);
        myAccountRepository.save(addAccount);
        logger.debug("添加到数据库的结果是{}", addAccount);
        return addAccount;
    }

    public List<MyAccountQueryResDTO> listMyAccount(Boolean decrypt) {
        List<MyAccount> all = myAccountRepository.findAll();
        if (decrypt) {
            for (MyAccount myAccount : all) {
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            }
        }
        return myAccountEntityListToResDTOList(all);
    }

    @Transactional(rollbackFor = Exception.class)
    public MyAccount updateMyAccount(MyAccountUpdateBO myAccountUpdateBO) {
        Optional<MyAccount> old = myAccountRepository.findById(myAccountUpdateBO.getId());
        if (old.isPresent()) {
            MyAccount updateAccount = old.get();
            myAccountUpdateBO.setUpdateTime(DateTimeUtil.nowChinaOffsetDateTime());
            BeanUtils.copyProperties(myAccountUpdateBO, updateAccount, "id", "createTime");
            updateAccount.setMyUsername(cryptoService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(cryptoService.encrypt(updateAccount.getMyPassword()));
            logger.debug("修改到数据库的数据是:{}", updateAccount);
            MyAccount updateRst = myAccountRepository.save(updateAccount);
            logger.debug("修改到数据库的结果是{}", updateRst);
            return updateRst;
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAccount(Integer id) {
        Optional<MyAccount> old = myAccountRepository.findById(id);
        if (old.isPresent()) {
            myAccountRepository.deleteById(id);
            return;
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    /**
     * 实体集合转换成响应体集合
     * @param myAccountList
     * @return
     */
    private List<MyAccountQueryResDTO> myAccountEntityListToResDTOList(List<MyAccount> myAccountList) {
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = new ArrayList<>(16);
        for (MyAccount myAccount : myAccountList) {
             MyAccountQueryResDTO resDTO = myAccountEntityToResDTO(myAccount);
            myAccountQueryResDTOList.add(resDTO);
        }
        return myAccountQueryResDTOList;
    }

    /**
     * 实体对象转换成响应体对象
     * @param myAccount
     * @return
     */
    private MyAccountQueryResDTO myAccountEntityToResDTO(MyAccount myAccount) {
        MyAccountQueryResDTO resDTO = new MyAccountQueryResDTO();
        BeanUtils.copyProperties(myAccount, resDTO);
        return resDTO;
    }

}
