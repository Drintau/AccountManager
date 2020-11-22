package mjhct.accountmanager.dao;

import mjhct.accountmanager.entity.MyAccount;
import org.springframework.data.repository.CrudRepository;

public interface MyAccountRepository extends CrudRepository<MyAccount, Integer> {
}
