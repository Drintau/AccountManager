package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.commons.util.PageUtil;
import drintau.accountmanager.webserver.dao.MyAccountRepository;
import drintau.accountmanager.webserver.domain.bo.MyAccountBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountImportAndExportBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountListBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountQueryConditionBO;
import drintau.accountmanager.webserver.domain.po.MyAccountPO;
import drintau.accountmanager.webserver.service.MyAccountService;
import drintau.accountmanager.webserver.service.SecureService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service(value = "myAccountService")
@Slf4j
public class MyAccountServiceImpl implements MyAccountService {

    @Resource
    private SecureService secureService;

    @Resource
    private MyAccountRepository myAccountRepository;

    @Override
    public MyAccountBO getMyAccountById(Integer id) {
        MyAccountPO myAccountPO = myAccountRepository.getById(id);
        if (myAccountPO != null) {
            MyAccountBO myAccount = BeanUtil.copy(myAccountPO, MyAccountBO.class);
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
        List<MyAccountPO> myAccountPOList = myAccountRepository.listByAppName(appName, myAccountListBO);
        Integer count = myAccountRepository.countByAppName(appName);

        List<MyAccountBO> myAccountBOList = new ArrayList<>();
        for (MyAccountPO po : myAccountPOList) {
            MyAccountBO bo = BeanUtil.copy(po, MyAccountBO.class);
            if (decrypt) {
                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
            }
            myAccountBOList.add(bo);
        }

        myAccountListBO.setList(myAccountBOList);
        myAccountListBO.setTotalRecords(count);
        myAccountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return myAccountListBO;
    }

    @Override
    public MyAccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize) {
        MyAccountListBO myAccountListBO = new MyAccountListBO(pageNumber, pageSize);
        List<MyAccountPO> myAccountPOList = myAccountRepository.list(myAccountListBO);
        Integer count = myAccountRepository.count();

        List<MyAccountBO> myAccountBOList = new ArrayList<>();
        for (MyAccountPO po : myAccountPOList) {
            MyAccountBO bo = BeanUtil.copy(po, MyAccountBO.class);
            if (decrypt) {
                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
            }
            myAccountBOList.add(bo);
        }

        myAccountListBO.setList(myAccountBOList);
        myAccountListBO.setTotalRecords(count);
        myAccountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return myAccountListBO;
    }

    @Override
    public MyAccountBO addMyAccount(MyAccountBO myAccountAddBO) {
        MyAccountPO addAccount = BeanUtil.copy(myAccountAddBO, MyAccountPO.class);
        addAccount.setMyUsername(secureService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(secureService.encrypt(addAccount.getMyPassword()));
        myAccountRepository.save(addAccount);
        return BeanUtil.copy(addAccount, MyAccountBO.class);
    }

    @Override
    public MyAccountBO updateMyAccount(MyAccountBO myAccountUpdateBO) {
        MyAccountPO updateAccount = myAccountRepository.getById(myAccountUpdateBO.getId());
        if (updateAccount != null) {
            updateAccount = BeanUtil.copy(myAccountUpdateBO, MyAccountPO.class);
            updateAccount.setMyUsername(secureService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(secureService.encrypt(updateAccount.getMyPassword()));
            myAccountRepository.update(updateAccount);
            return BeanUtil.copy(updateAccount, MyAccountBO.class);
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Override
    public void deleteMyAccount(Integer id) {
        myAccountRepository.deleteById(id);
    }

    @Override
    public List<MyAccountImportAndExportBO> exportMyAccounts() {
        // 查询所有数据
        List<MyAccountPO> poList = myAccountRepository.list();

        List<MyAccountImportAndExportBO> exportDataList = new ArrayList<>(poList.size() * 2);
        for (MyAccountPO po : poList) {
            MyAccountImportAndExportBO exportBO = BeanUtil.copy(po, MyAccountImportAndExportBO.class);
            exportBO.setMyUsername(secureService.decrypt(po.getMyUsername()));
            exportBO.setMyPassword(secureService.decrypt(po.getMyPassword()));
            exportDataList.add(exportBO);
        }

        return exportDataList;
    }

    @Override
    public void importMyAccounts(List<MyAccountImportAndExportBO> importDataList) {
        List<MyAccountPO> importPOS = new ArrayList<>(importDataList.size() * 2);
        for (MyAccountImportAndExportBO importAccount : importDataList) {
            MyAccountPO insertPO = BeanUtil.copy(importAccount, MyAccountPO.class);
            insertPO.setMyUsername(secureService.encrypt(insertPO.getMyUsername()));
            insertPO.setMyPassword(secureService.encrypt(insertPO.getMyPassword()));
            importPOS.add(insertPO);
        }
        myAccountRepository.saveBatch(importPOS);
    }

}
