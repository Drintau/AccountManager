package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.po.CategoryPO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends ListCrudRepository<CategoryPO, Integer> {

    // 查询全部：findAll()
    List<CategoryPO> findAllByOrderById();

    // 保存一个：save()

    // 修改一个：save()

    // 删除一个：deleteById()

    // 查询一个分类名称是否存在
    boolean existsByCategoryName(String categoryName);

    // 根据名称查询一个
    Optional<CategoryPO> findByCategoryName(String categoryName);
}
