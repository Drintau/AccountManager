package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.po.AccountPO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ListCrudRepository<AccountPO, Integer> {

    // 查询全部：findAll()

    // 保存一个：save()

}
