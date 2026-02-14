package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.domain.bo.CategoryBO;
import drintau.accountmanager.webserver.domain.vo.*;
import drintau.accountmanager.webserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/all")
    public CommonResult<CategoryAllResVO> all() {
        List<CategoryBO> allCategoryBOList = categoryService.allCategory();
        CategoryAllResVO resVO = new CategoryAllResVO();
        resVO.setList(BeanUtil.copyList(allCategoryBOList, CategoryVO.class));
        return new CommonResult<>(BusinessCode.SUCCESS, resVO);
    }

    @PostMapping("/add")
    public CommonResult<CategoryVO> add(@RequestBody @Validated CategoryAddReqVO reqVO) {
        CategoryBO bo = new CategoryBO();
        bo.setCategoryName(reqVO.getCategoryName());
        bo = categoryService.addCategory(bo);
        return new CommonResult<>(BusinessCode.SUCCESS, BeanUtil.copy(bo, CategoryVO.class));
    }

    @PostMapping("/update")
    public CommonResult<CategoryVO> update(@RequestBody @Validated CategoryUpdateReqVO reqVO) {
        CategoryBO bo = BeanUtil.copy(reqVO, CategoryBO.class);
        categoryService.updateCategory(bo);
        return new CommonResult<>(BusinessCode.SUCCESS, BeanUtil.copy(bo, CategoryVO.class));
    }

    @PostMapping("/delete")
    public CommonResult<Void> delete(@RequestBody @Validated CategoryDeleteReqVO reqVO) {
        categoryService.deleteCategory(reqVO.getId());
        return new CommonResult<>(BusinessCode.SUCCESS);
    }

}
