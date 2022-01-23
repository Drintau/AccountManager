package mjhct.accountmanager.dao.impl;

import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.domain.bo.PageBO;
import mjhct.accountmanager.domain.entity.MyAccountPO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MyAccountRepositoryImpl implements MyAccountRepository {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(MyAccountPO po) {
        String sql = "insert into \"my_account\"(\"app_name\", \"app_url\", \"my_username\", \"my_password\", \"remark\") values (:appName, :appUrl, :myUsername, :myPassword, :remark)";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(po));
    }

    @Override
    public void batchSave(List<MyAccountPO> pos) {

    }

    @Override
    public void update(MyAccountPO po) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public MyAccountPO getById(Integer id) {
        String sql = "select * from \"my_account\" where id = :id";
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, MyAccountPO.class);
    }

    @Override
    public List<MyAccountPO> listAll(PageBO pageBO) {
        String sql = "select * from \"my_account\"";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MyAccountPO.class));
    }

    @Override
    public List<MyAccountPO> listByAppName(String appName, PageBO pageBO) {
        return null;
    }
}
