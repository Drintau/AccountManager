package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.webserver.domain.bo.CategoryBO;
import drintau.accountmanager.webserver.domain.vo.CategoryAllResVO;
import drintau.accountmanager.webserver.domain.vo.CategoryVO;
import drintau.accountmanager.webserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
        return new CommonResult<>(CommonCode.SUCCESS, resVO);
    }
}
