package mjhct.accountmanager.dao;

import mjhct.accountmanager.domain.entity.MyAccountPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyAccountRepository extends JpaRepository<MyAccountPO, Integer> {

}
