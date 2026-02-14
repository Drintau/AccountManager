package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.shared.util.NumberUtil;
import drintau.accountmanager.webserver.dao.AccountRepository;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.CategoryBO;
import drintau.accountmanager.webserver.domain.po.AccountPO;
import drintau.accountmanager.webserver.service.AccountService;
import drintau.accountmanager.webserver.service.CategoryService;
import drintau.accountmanager.webserver.service.SecureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final SecureService secureService;

    private final CategoryService categoryService;

    @Override
    public AccountBO getAccount(Integer id) {
        Optional<AccountPO> poOptional = accountRepository.findById(id);
        if (poOptional.isEmpty()) {
            throw new BusinessException("账号id对应数据不存在");
        }

        AccountBO bo = BeanUtil.copy(poOptional.get(), AccountBO.class);
        bo.setUsername(secureService.decrypt(bo.getUsername()));
        bo.setPassword(secureService.decrypt(bo.getPassword()));

        if (NumberUtil.isNotNullAndGreaterThanZero(bo.getCategoryId())) {
            CategoryBO categoryBO = categoryService.getCategory(bo.getCategoryId());
            bo.setCategoryName(categoryBO.getCategoryName());
        }

        return bo;
    }

    @Override
    public AccountBO addAccount(AccountBO bo) {
        AccountPO po = BeanUtil.copy(bo, AccountPO.class);
        po.setUsername(secureService.encrypt(po.getUsername()));
        po.setPassword(secureService.encrypt(po.getPassword()));
        accountRepository.save(po);
        return BeanUtil.copy(po, AccountBO.class);
    }

//    @Override
//    public AccountFindResultBO queryMyAccount(AccountFindConditionBO condition) {
//        // 按照条件的优先级进行查询，只要进行了某条件查询就返回；如没有条件匹配则走无条件的查询
//
//        // 按照应用名称模糊查询
//        if (StringUtils.hasText(condition.getFuzzyName())) {
//            return listMyAccountByAppName(condition.getDecrypt(), condition.getFuzzyName(), condition.getPageNumber(), condition.getPageSize());
//        }
//
//        // 无条件分页查询
//        return listMyAccount(condition.getDecrypt(), condition.getPageNumber(), condition.getPageSize());
//    }
//
//    private AccountFindResultBO listMyAccountByAppName(Boolean decrypt, String appName, int pageNumber, int pageSize) {
//        AccountFindResultBO accountFindResultBO = new AccountFindResultBO(pageNumber, pageSize);
//        List<MyAccountPO> myAccountPOList = myAccountRepository.listByAppName(appName, accountFindResultBO);
//        Integer count = myAccountRepository.countByAppName(appName);
//
//        List<AccountBO> accountBOList = new ArrayList<>();
//        for (MyAccountPO po : myAccountPOList) {
//            AccountBO bo = BeanUtil.copy(po, AccountBO.class);
//            if (decrypt) {
//                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
//                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
//            }
//            accountBOList.add(bo);
//        }
//
//        accountFindResultBO.setList(accountBOList);
//        accountFindResultBO.setTotalRecords(count);
//        accountFindResultBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
//        return accountFindResultBO;
//    }
//
//    @Override
//    public AccountFindResultBO listMyAccount(Boolean decrypt, int pageNumber, int pageSize) {
//        AccountFindResultBO accountFindResultBO = new AccountFindResultBO(pageNumber, pageSize);
//        List<MyAccountPO> myAccountPOList = myAccountRepository.list(accountFindResultBO);
//        Integer count = myAccountRepository.count();
//
//        List<AccountBO> accountBOList = new ArrayList<>();
//        for (MyAccountPO po : myAccountPOList) {
//            AccountBO bo = BeanUtil.copy(po, AccountBO.class);
//            if (decrypt) {
//                bo.setMyUsername(secureService.decrypt(po.getMyUsername()));
//                bo.setMyPassword(secureService.decrypt(po.getMyPassword()));
//            }
//            accountBOList.add(bo);
//        }
//
//        accountFindResultBO.setList(accountBOList);
//        accountFindResultBO.setTotalRecords(count);
//        accountFindResultBO.setTotalPages(PageUtil.calcTotalPages(count, pageSize));
//        return accountFindResultBO;
//    }
//
//
//    @Override
//    public AccountBO updateMyAccount(AccountBO myAccountUpdateBO) {
//        MyAccountPO updateAccount = myAccountRepository.getById(myAccountUpdateBO.getId());
//        if (updateAccount != null) {
//            updateAccount = BeanUtil.copy(myAccountUpdateBO, MyAccountPO.class);
//            updateAccount.setMyUsername(secureService.encrypt(updateAccount.getMyUsername()));
//            updateAccount.setMyPassword(secureService.encrypt(updateAccount.getMyPassword()));
//            myAccountRepository.update(updateAccount);
//            return BeanUtil.copy(updateAccount, AccountBO.class);
//        }
//        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
//    }
//
//    @Override
//    public void deleteMyAccount(Integer id) {
//        myAccountRepository.deleteById(id);
//    }
//
//    @Override
//    public List<AccountIEBO> exportMyAccounts() {
//        // 查询所有数据
//        List<MyAccountPO> poList = myAccountRepository.list();
//
//        List<AccountIEBO> exportDataList = new ArrayList<>(poList.size() * 2);
//        for (MyAccountPO po : poList) {
//            AccountIEBO exportBO = BeanUtil.copy(po, AccountIEBO.class);
//            exportBO.setMyUsername(secureService.decrypt(po.getMyUsername()));
//            exportBO.setMyPassword(secureService.decrypt(po.getMyPassword()));
//            exportDataList.add(exportBO);
//        }
//
//        return exportDataList;
//    }
//
//    @Override
//    public void importMyAccounts(List<AccountIEBO> importDataList) {
//        List<MyAccountPO> importPOS = new ArrayList<>(importDataList.size() * 2);
//        for (AccountIEBO importAccount : importDataList) {
//            MyAccountPO insertPO = BeanUtil.copy(importAccount, MyAccountPO.class);
//            insertPO.setMyUsername(secureService.encrypt(insertPO.getMyUsername()));
//            insertPO.setMyPassword(secureService.encrypt(insertPO.getMyPassword()));
//            importPOS.add(insertPO);
//        }
//        myAccountRepository.saveBatch(importPOS);
//    }

}
