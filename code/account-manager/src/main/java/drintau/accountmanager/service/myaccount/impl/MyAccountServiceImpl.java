package drintau.accountmanager.service.myaccount.impl;

import drintau.accountmanager.commons.CommonCode;
import drintau.accountmanager.dao.MyAccountRepository;
import drintau.accountmanager.domain.bo.*;
import drintau.accountmanager.domain.entity.MyAccountPO;
import drintau.accountmanager.exception.BusinessException;
import drintau.accountmanager.service.myaccount.MyAccountService;
import drintau.accountmanager.service.secure.SecureService;
import drintau.accountmanager.util.BeanUtil;
import drintau.accountmanager.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "myAccountService")
public class MyAccountServiceImpl implements MyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountServiceImpl.class);

    @Resource
    private SecureService secureService;

    @Resource
    private MyAccountRepository myAccountRepository;

    @Override
    public MyAccountInfoBO getMyAccountById(Integer id) {
        MyAccountPO myAccountPO = myAccountRepository.getById(id);
        if (myAccountPO != null) {
            MyAccountInfoBO myAccount = BeanUtil.copy(myAccountPO, MyAccountInfoBO.class);
            myAccount.setMyUsername(secureService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(secureService.decrypt(myAccount.getMyPassword()));
            return myAccount;
        }
        return null;
    }

    @Override
    public MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition) {
        // 按照条件的优先级进行查询，只要进行了某条件查询就返回；如没有条件匹配则走无条件的查询

        // 按照应用名称模糊查询
        if (StringUtils.hasText(condition.getFuzzyName())) {
            return listMyAccountByAppName(condition.getDecrypt(), condition.getFuzzyName(), condition.getPageNumber(), condition.getPageSize());
        }

        // 无条件分页查询
        return listMyAccount(condition.getDecrypt(), condition.getPageNumber(), condition.getPageSize());
    }

    private MyAccountListBO listMyAccountByAppName(Boolean decrypt, String appName, int pageNumber, int pageSize) {
        MyAccountListBO myAccountListBO = new MyAccountListBO(pageNumber, pageSize);
        List<MyAccountPO> dataList = myAccountRepository.listByAppName(appName, myAccountListBO);
        Integer count = myAccountRepository.countByAppName(appName);
        if (decrypt) {
            for (MyAccountPO myAccount : dataList) {
                myAccount.setMyUsername(secureService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(secureService.decrypt(myAccount.getMyPassword()));
            }
        }
        List<MyAccountInfoBO> myAccountInfoBOS = BeanUtil.copyList(dataList, MyAccountInfoBO.class);
        myAccountListBO.setList(myAccountInfoBOS);
        myAccountListBO.setTotalRecords(count);
        myAccountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return myAccountListBO;
    }

    @Override
    public MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize) {
        MyAccountListBO myAccountListBO = new MyAccountListBO(pageNumber, pageSize);
        List<MyAccountPO> dataList = myAccountRepository.list(myAccountListBO);
        Integer count = myAccountRepository.count();
        if (decrypt) {
            for (MyAccountPO myAccount : dataList) {
                myAccount.setMyUsername(secureService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(secureService.decrypt(myAccount.getMyPassword()));
            }
        }
        List<MyAccountInfoBO> myAccountInfoBOS = BeanUtil.copyList(dataList, MyAccountInfoBO.class);
        myAccountListBO.setList(myAccountInfoBOS);
        myAccountListBO.setTotalRecords(count);
        myAccountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return myAccountListBO;
    }

    @Override
    public MyAccountInfoBO addMyAccount(MyAccountAddInfoBO myAccountAddBO) {
        MyAccountPO addAccount = BeanUtil.copy(myAccountAddBO, MyAccountPO.class);
        addAccount.setMyUsername(secureService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(secureService.encrypt(addAccount.getMyPassword()));
        myAccountRepository.save(addAccount);
        return BeanUtil.copy(addAccount, MyAccountInfoBO.class);
    }

    @Override
    public MyAccountInfoBO updateMyAccount(MyAccountUpdateInfoBO myAccountUpdateBO) {
        MyAccountPO updateAccount = myAccountRepository.getById(myAccountUpdateBO.getId());
        if (updateAccount != null) {
            updateAccount = BeanUtil.copy(myAccountUpdateBO, MyAccountPO.class);
            updateAccount.setMyUsername(secureService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(secureService.encrypt(updateAccount.getMyPassword()));
            myAccountRepository.update(updateAccount);
            return BeanUtil.copy(updateAccount, MyAccountInfoBO.class);
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Override
    public void deleteMyAccount(Integer id) {
        myAccountRepository.deleteById(id);
    }

    @Override
    public List<MyAccountImportAndExportInfoBO> exportAccounts() {
        // 查询所有数据
        List<MyAccountPO> rawDataList = myAccountRepository.list();
        List<MyAccountImportAndExportInfoBO> exportDataList = BeanUtil.copyList(rawDataList, MyAccountImportAndExportInfoBO.class);
        // 解密
        for (MyAccountImportAndExportInfoBO myAccount : exportDataList) {
            myAccount.setMyUsername(secureService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(secureService.decrypt(myAccount.getMyPassword()));
        }
        return exportDataList;
    }

    @Override
    public void importAccounts(List<MyAccountImportAndExportInfoBO> importDataList) {
        List<MyAccountPO> importPOS = new ArrayList<>(importDataList.size() * 2);
        for (MyAccountImportAndExportInfoBO importAccount : importDataList) {
            MyAccountPO insertPO = BeanUtil.copy(importAccount, MyAccountPO.class);
            insertPO.setMyUsername(secureService.encrypt(insertPO.getMyUsername()));
            insertPO.setMyPassword(secureService.encrypt(insertPO.getMyPassword()));
            importPOS.add(insertPO);
        }
        myAccountRepository.saveBatch(importPOS);
    }

}