package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.commons.util.PageUtil;
import drintau.accountmanager.webserver.dao.MyAccountRepository;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.AccountIEBO;
import drintau.accountmanager.webserver.domain.bo.AccountListBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindBO;
import drintau.accountmanager.webserver.service.AccountService;
import drintau.accountmanager.webserver.service.SecureService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service(value = "myAccountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private SecureService secureService;

    @Resource
    private MyAccountRepository myAccountRepository;

    @Override
    public AccountBO getMyAccountById(Integer id) {
        MyAccountPO myAccountPO = myAccountRepository.getById(id);
        if (myAccountPO != null) {
            AccountBO myAccount = BeanUtil.copy(myAccountPO, AccountBO.class);
            myAccount.setMyUsername(secureService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(secureService.decrypt(myAccount.getMyPassword()));
            return myAccount;
        }
        return null;
    }

    @Override
    public AccountListBO queryMyAccount(AccountFindBO condition) {
        // 按照条件的优先级进行查询，只要进行了某条件查询就返回；如没有条件匹配则走无条件的查询

        // 按照应用名称模糊查询
        if (StringUtils.hasText(condition.getFuzzyName())) {
            return listMyAccountByAppName(condition.getDecrypt(), condition.getFuzzyName(), condition.getPageNumber(), condition.getPageSize());
        }

        // 无条件分页查询
        return listMyAccount(condition.getDecrypt(), condition.getPageNumber(), condition.getPageSize());
    }

    private AccountListBO listMyAccountByAppName(Boolean decrypt, String appName, int pageNumber, int pageSize) {
        AccountListBO accountListBO = new AccountListBO(pageNumber, pageSize);
        List<MyAccountPO> myAccountPOList = myAccountRepository.listByAppName(appName, accountListBO);
        Integer count = myAccountRepository.countByAppName(appName);

        List<AccountBO> accountBOList = new ArrayList<>();
        for (MyAccountPO po : myAccountPOList) {
            AccountBO bo = BeanUtil.copy(po, AccountBO.class);
            if (decrypt) {
                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
            }
            accountBOList.add(bo);
        }

        accountListBO.setList(accountBOList);
        accountListBO.setTotalRecords(count);
        accountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return accountListBO;
    }

    @Override
    public AccountListBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize) {
        AccountListBO accountListBO = new AccountListBO(pageNumber, pageSize);
        List<MyAccountPO> myAccountPOList = myAccountRepository.list(accountListBO);
        Integer count = myAccountRepository.count();

        List<AccountBO> accountBOList = new ArrayList<>();
        for (MyAccountPO po : myAccountPOList) {
            AccountBO bo = BeanUtil.copy(po, AccountBO.class);
            if (decrypt) {
                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
            }
            accountBOList.add(bo);
        }

        accountListBO.setList(accountBOList);
        accountListBO.setTotalRecords(count);
        accountListBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
        return accountListBO;
    }

    @Override
    public AccountBO addMyAccount(AccountBO myAccountAddBO) {
        MyAccountPO addAccount = BeanUtil.copy(myAccountAddBO, MyAccountPO.class);
        addAccount.setMyUsername(secureService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(secureService.encrypt(addAccount.getMyPassword()));
        myAccountRepository.save(addAccount);
        return BeanUtil.copy(addAccount, AccountBO.class);
    }

    @Override
    public AccountBO updateMyAccount(AccountBO myAccountUpdateBO) {
        MyAccountPO updateAccount = myAccountRepository.getById(myAccountUpdateBO.getId());
        if (updateAccount != null) {
            updateAccount = BeanUtil.copy(myAccountUpdateBO, MyAccountPO.class);
            updateAccount.setMyUsername(secureService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(secureService.encrypt(updateAccount.getMyPassword()));
            myAccountRepository.update(updateAccount);
            return BeanUtil.copy(updateAccount, AccountBO.class);
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Override
    public void deleteMyAccount(Integer id) {
        myAccountRepository.deleteById(id);
    }

    @Override
    public List<AccountIEBO> exportMyAccounts() {
        // 查询所有数据
        List<MyAccountPO> poList = myAccountRepository.list();

        List<AccountIEBO> exportDataList = new ArrayList<>(poList.size() * 2);
        for (MyAccountPO po : poList) {
            AccountIEBO exportBO = BeanUtil.copy(po, AccountIEBO.class);
            exportBO.setMyUsername(secureService.decrypt(po.getMyUsername()));
            exportBO.setMyPassword(secureService.decrypt(po.getMyPassword()));
            exportDataList.add(exportBO);
        }

        return exportDataList;
    }

    @Override
    public void importMyAccounts(List<AccountIEBO> importDataList) {
        List<MyAccountPO> importPOS = new ArrayList<>(importDataList.size() * 2);
        for (AccountIEBO importAccount : importDataList) {
            MyAccountPO insertPO = BeanUtil.copy(importAccount, MyAccountPO.class);
            insertPO.setMyUsername(secureService.encrypt(insertPO.getMyUsername()));
            insertPO.setMyPassword(secureService.encrypt(insertPO.getMyPassword()));
            importPOS.add(insertPO);
        }
        myAccountRepository.saveBatch(importPOS);
    }

}
