package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.dao.AccountRepository;
import drintau.accountmanager.webserver.dao.CategoryRepository;
import drintau.accountmanager.webserver.domain.bo.CategoryBO;
import drintau.accountmanager.webserver.domain.po.CategoryPO;
import drintau.accountmanager.webserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<CategoryBO> allCategory() {
        List<CategoryPO> allCategoryPOList = categoryRepository.findAllByOrderById();
        return BeanUtil.copyList(allCategoryPOList, CategoryBO.class);
    }

    @Override
    public CategoryBO addCategory(CategoryBO categoryBO) {
        boolean duplicateFlag = categoryRepository.existsByCategoryName(categoryBO.getCategoryName());
        if (duplicateFlag) {
            throw new BusinessException("分类重名");
        }

        CategoryPO po = BeanUtil.copy(categoryBO, CategoryPO.class);
        categoryRepository.save(po);
        categoryBO.setId(po.getId());
        return categoryBO;
    }

    @Override
    public void updateCategory(CategoryBO categoryBO) {
        Optional<CategoryPO> poOptional = categoryRepository.findById(categoryBO.getId());
        if (poOptional.isEmpty()) {
            throw new BusinessException("分类id对应数据不存在");
        }

        Optional<CategoryPO> existPO = categoryRepository.findByCategoryName(categoryBO.getCategoryName());
        if (existPO.isPresent() && !existPO.get().getId().equals(categoryBO.getId())) {
            throw new BusinessException("分类重名");
        }

        CategoryPO po = BeanUtil.copy(categoryBO, CategoryPO.class);
        categoryRepository.save(po);
    }

    @Override
    public void deleteCategory(Integer id) {
        boolean existFlag = accountRepository.existsByCategoryId(id);
        if (existFlag) {
            throw new BusinessException("分类下有账号数据，不可删除");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryBO getCategory(Integer id) {
        Optional<CategoryPO> poOptional = categoryRepository.findById(id);
        if (poOptional.isEmpty()) {
            throw new BusinessException("分类id对应数据不存在");
        }
        return BeanUtil.copy(poOptional.get(), CategoryBO.class);
    }

}
