package mjhct.accountmanager.dao;

import mjhct.accountmanager.entity.MyAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MyAccountRepository extends CrudRepository<MyAccount, Integer> {

    List<MyAccount> findByAppName(String appName);

}
