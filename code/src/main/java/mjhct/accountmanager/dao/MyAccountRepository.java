package mjhct.accountmanager.dao;

import mjhct.accountmanager.domain.entity.MyAccountPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MyAccountRepository extends PagingAndSortingRepository<MyAccountPO, Integer> {

    List<MyAccountPO> findAll();

    // 需要自己拼%
    Page<MyAccountPO> findByAppNameLike(String appName, Pageable pageable);

    // appName%
    Page<MyAccountPO> findByAppNameStartingWith(String appName, Pageable pageable);

    // %appName
    Page<MyAccountPO> findByAppNameEndingWith(String appName, Pageable pageable);

    // %appName%
    Page<MyAccountPO> findByAppNameContaining(String appName, Pageable pageable);

}
