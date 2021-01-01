package mjhct.accountmanager.dao;

import mjhct.accountmanager.entity.MyAccountPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyAccountRepository extends JpaRepository<MyAccountPO, Integer> {

    List<MyAccountPO> findByAppName(String appName);

}
