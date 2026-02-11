package drintau.accountmanager.webserver.dao;

import drintau.accountmanager.webserver.domain.po.ConfigPO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends ListCrudRepository<ConfigPO, Integer> {

    // 查询全部：findAll()

    // 保存一个：save()

    // 修改
    @Modifying
    @Query("UPDATE config SET config_value = :configValue WHERE config_key = :configKey")
    int updateConfigValueByKey(@Param("configKey") String configKey,
                               @Param("configValue") String configValue);

}
