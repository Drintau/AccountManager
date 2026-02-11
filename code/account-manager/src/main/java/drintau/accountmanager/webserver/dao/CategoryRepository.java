package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.po.CategoryPO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ListCrudRepository<CategoryPO, Integer> {

    // 查询全部：findAll()

    // 保存一个：save()

}
