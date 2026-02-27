package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.*;
import drintau.accountmanager.webserver.dao.AccountDynamicRepository;
import drintau.accountmanager.webserver.dao.AccountStaticRepository;
import drintau.accountmanager.webserver.domain.bo.*;
import drintau.accountmanager.webserver.domain.po.AccountPO;
import drintau.accountmanager.webserver.service.AccountService;
import drintau.accountmanager.webserver.service.CategoryService;
import drintau.accountmanager.webserver.service.SecureService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountStaticRepository accountStaticRepository;

    private final AccountDynamicRepository accountDynamicRepository;

    private final SecureService secureService;

    private final CategoryService categoryService;

    @Override
    public AccountBO getAccount(Integer id) {
        Optional<AccountPO> poOptional = accountStaticRepository.findById(id);
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
        accountStaticRepository.save(po);

        bo.setId(po.getId());
        bo.setUsername(po.getUsername());
        bo.setPassword(po.getPassword());
        return bo;
    }

    @Override
    public AccountBO updateAccount(AccountBO bo) {
        Optional<AccountPO> poOptional = accountStaticRepository.findById(bo.getId());
        if (poOptional.isEmpty()) {
            throw new BusinessException("账号id对应数据不存在");
        }

        if (NumberUtil.isNotNullAndGreaterThanZero(bo.getCategoryId())) {
            CategoryBO category = categoryService.getCategory(bo.getCategoryId());
            bo.setCategoryName(category.getCategoryName());
        }

        AccountPO oldPO = poOptional.get();

        AccountPO po = BeanUtil.copy(bo, AccountPO.class);
        po.setUsername(secureService.encrypt(po.getUsername()));
        po.setPassword(secureService.encrypt(po.getPassword()));
        po.setCreateTime(oldPO.getCreateTime());
        po.setUpdateTime(DateTimeUtil.getCurrentUtcSecond());
        accountStaticRepository.save(po);

        bo.setUsername(po.getUsername());
        bo.setPassword(po.getPassword());
        return bo;
    }

    @Override
    public void deleteAccount(Integer id) {
        boolean existFlag = accountStaticRepository.existsById(id);
        if (!existFlag) {
            throw new BusinessException("账号id对应数据不存在");
        }

        accountStaticRepository.deleteById(id);
    }

    @Override
    public AccountFindResultBO findAccount(AccountFindConditionBO conditionBO) {
        AccountFindResultBO resultBO = new AccountFindResultBO();
        resultBO.setPageNum(conditionBO.getPageNum());
        resultBO.setPageSize(conditionBO.getPageSize());

        Integer total = accountDynamicRepository.countByCondition(conditionBO);

        if (NumberUtil.isNotNullAndGreaterThanZero(total)) {
            resultBO.setTotal(total);
            resultBO.setPages(PageUtil.calcPages(total, conditionBO.getPageSize()));

            List<AccountPO> poList = accountDynamicRepository.findByCondition(conditionBO);
            List<AccountBO> boList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(poList)) {
                // 查分类，补充分类名称
                List<CategoryBO> allCategory = categoryService.allCategory();
                Map<Integer, String> categoryId2NameMap = allCategory.stream().collect(Collectors.toMap(CategoryBO::getId, CategoryBO::getCategoryName));

                for (AccountPO po : poList) {
                    AccountBO bo = BeanUtil.copy(po, AccountBO.class);
                    bo.setUsername(secureService.decrypt(bo.getUsername()));
                    bo.setPassword(secureService.decrypt(bo.getPassword()));
                    if (bo.getCategoryId() != null) {
                        bo.setCategoryName(categoryId2NameMap.get(bo.getCategoryId()));
                    }
                    boList.add(bo);
                }
            }
            resultBO.setList(boList);
        } else {
            resultBO.setTotal(0);
            resultBO.setPages(0);
        }

        return resultBO;
    }

    @Override
    public void importAccount(List<AccountTransferBO> importDataList) {
        List<CategoryBO> allCategory = categoryService.allCategory();
        Map<String, Integer> categoryName2IdMap = allCategory.stream().collect(Collectors.toMap(CategoryBO::getCategoryName, CategoryBO::getId));

        List<AccountPO> importPOList = new ArrayList<>(importDataList.size() * 2);
        for (AccountTransferBO accountTransferBO : importDataList) {
            AccountPO importPO = BeanUtil.copy(accountTransferBO, AccountPO.class);
            if (StrUtil.isNotBlank(accountTransferBO.getCategoryName())) {
                Integer categoryId = categoryName2IdMap.get(accountTransferBO.getCategoryName());
                if (categoryId == null) {
                    CategoryBO newCategoryBO = new CategoryBO();
                    newCategoryBO.setCategoryName(accountTransferBO.getCategoryName());
                    newCategoryBO = categoryService.addCategory(newCategoryBO);
                    categoryName2IdMap.put(newCategoryBO.getCategoryName(), newCategoryBO.getId());
                    categoryId = newCategoryBO.getId();
                }
                importPO.setCategoryId(categoryId);
            }
            importPO.setUsername(secureService.encrypt(importPO.getUsername()));
            importPO.setPassword(secureService.encrypt(importPO.getPassword()));
            long currentUtcSecond = DateTimeUtil.getCurrentUtcSecond();
            importPO.setCreateTime(currentUtcSecond);
            importPO.setUpdateTime(currentUtcSecond);
            importPOList.add(importPO);
        }

        if (CollectionUtils.isNotEmpty(importPOList)) {
            accountStaticRepository.saveAll(importPOList);
        }
    }

    @Override
    public List<AccountTransferBO> exportAccount() {
        AccountFindResultBO resultBO = findAccount(new AccountFindConditionBO());
        return BeanUtil.copyList(resultBO.getList(), AccountTransferBO.class);
    }

}
