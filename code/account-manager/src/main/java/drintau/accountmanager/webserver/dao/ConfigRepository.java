package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.po.ConfigPO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends ListCrudRepository<ConfigPO, Integer> {

    // 查询全部：findAll()

    // 保存一个：save()

}
