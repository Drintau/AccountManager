package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.CategoryBO;

import java.util.List;

public interface CategoryService {

    List<CategoryBO> allCategory();

    CategoryBO addCategory(CategoryBO categoryBO);

    void updateCategory(CategoryBO categoryBO);

    void deleteCategory(Integer id);

    CategoryBO getCategory(Integer id);

}
