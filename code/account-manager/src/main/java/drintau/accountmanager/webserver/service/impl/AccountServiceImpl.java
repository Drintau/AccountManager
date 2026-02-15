package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.shared.util.DateTimeUtil;
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
        if (NumberUtil.isNotNullAndGreaterThanZero(bo.getCategoryId())) {
            CategoryBO category = categoryService.getCategory(bo.getCategoryId());
            bo.setCategoryName(category.getCategoryName());
        }

        AccountPO po = BeanUtil.copy(bo, AccountPO.class);
        po.setUsername(secureService.encrypt(po.getUsername()));
        po.setPassword(secureService.encrypt(po.getPassword()));
        long currentUtcSecond = DateTimeUtil.getCurrentUtcSecond();
        po.setCreateTime(currentUtcSecond);
        po.setUpdateTime(currentUtcSecond);
        accountRepository.save(po);

        bo.setId(po.getId());
        bo.setUsername(po.getUsername());
        bo.setPassword(po.getPassword());
        return bo;
    }

    @Override
    public AccountBO updateAccount(AccountBO bo) {
        boolean existFlag = accountRepository.existsById(bo.getId());
        if (!existFlag) {
            throw new BusinessException("账号id对应数据不存在");
        }

        if (NumberUtil.isNotNullAndGreaterThanZero(bo.getCategoryId())) {
            CategoryBO category = categoryService.getCategory(bo.getCategoryId());
            bo.setCategoryName(category.getCategoryName());
        }

        AccountPO po = BeanUtil.copy(bo, AccountPO.class);
        po.setUsername(secureService.encrypt(po.getUsername()));
        po.setPassword(secureService.encrypt(po.getPassword()));
        po.setUpdateTime(DateTimeUtil.getCurrentUtcSecond());
        accountRepository.save(po);

        bo.setUsername(po.getUsername());
        bo.setPassword(po.getPassword());
        return bo;
    }

    @Override
    public void deleteAccount(Integer id) {
        boolean existFlag = accountRepository.existsById(id);
        if (!existFlag) {
            throw new BusinessException("账号id对应数据不存在");
        }

        accountRepository.deleteById(id);
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
//                bo.setusername(secureService.decrypt(po.getusername()));
//                bo.setpassword(secureService.decrypt(po.getpassword()));
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
//                bo.setusername(secureService.decrypt(po.getusername()));
//                bo.setpassword(secureService.decrypt(po.getpassword()));
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
//    public List<AccountIEBO> exportMyAccounts() {
//        // 查询所有数据
//        List<MyAccountPO> poList = myAccountRepository.list();
//
//        List<AccountIEBO> exportDataList = new ArrayList<>(poList.size() * 2);
//        for (MyAccountPO po : poList) {
//            AccountIEBO exportBO = BeanUtil.copy(po, AccountIEBO.class);
//            exportBO.setusername(secureService.decrypt(po.getusername()));
//            exportBO.setpassword(secureService.decrypt(po.getpassword()));
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
//            insertPO.setusername(secureService.encrypt(insertPO.getusername()));
//            insertPO.setpassword(secureService.encrypt(insertPO.getpassword()));
//            importPOS.add(insertPO);
//        }
//        myAccountRepository.saveBatch(importPOS);
//    }

}
