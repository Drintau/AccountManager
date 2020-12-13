package mjhct.accountmanager.dao;

import mjhct.accountmanager.entity.MyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyAccountRepository extends JpaRepository<MyAccount, Integer> {

    List<MyAccount> findByAppName(String appName);

}
