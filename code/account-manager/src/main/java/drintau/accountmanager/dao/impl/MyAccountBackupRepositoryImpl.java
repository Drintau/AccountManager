package drintau.accountmanager.dao.impl;

import drintau.accountmanager.dao.MyAccountBackupRepository;
import drintau.accountmanager.domain.entity.MyAccountPO;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MyAccountBackupRepositoryImpl implements MyAccountBackupRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void truncate() {
        String sql = "truncate table my_account";
        namedParameterJdbcTemplate.update(sql, new HashMap<>());
    }

    @Override
    public void insertBatch(List<MyAccountPO> pos) {
        String sql = "insert into my_account(app_name, app_url, my_username, my_password, remark, create_time, update_time) values (:appName, :appUrl, :myUsername, :myPassword, :remark, :createTime, :updateTime)";
        SqlParameterSource[] batchArgs = new SqlParameterSource[pos.size()];
        for (int i = 0; i < pos.size(); i++) {
            batchArgs[i] = new BeanPropertySqlParameterSource(pos.get(i));
        }
        namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
    }

}
