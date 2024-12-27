package drintau.accountmanager.webserver.dao.impl;

import drintau.accountmanager.webserver.dao.MyAccountRepository;
import drintau.accountmanager.webserver.domain.bo.PageBO;
import drintau.accountmanager.webserver.domain.po.MyAccountPO;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考博客：https://blog.csdn.net/u011179993/article/details/74791304
 */
@Repository
public class MyAccountRepositoryImpl implements MyAccountRepository {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(MyAccountPO po) {
        String sql = "insert into my_account(app_name, app_url, my_username, my_password, remark) values (:appName, :appUrl, :myUsername, :myPassword, :remark)";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(po));
    }

    @Override
    public void saveBatch(List<MyAccountPO> pos) {
        String sql = "insert into my_account(app_name, app_url, my_username, my_password, remark) values (:appName, :appUrl, :myUsername, :myPassword, :remark)";
        SqlParameterSource[] batchArgs = new SqlParameterSource[pos.size()];
        for (int i = 0; i < pos.size(); i++) {
            batchArgs[i] = new BeanPropertySqlParameterSource(pos.get(i));
        }
        namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "delete from my_account where id = :id";
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void update(MyAccountPO po) {
        String sql = "update my_account set app_name = :appName, app_url = :appUrl, my_username = :myUsername, my_password = :myPassword, remark = :remark where id = :id";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(po));
    }

    @Override
    public MyAccountPO getById(Integer id) {
        String sql = "select * from my_account where id = :id";
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(MyAccountPO.class));
    }

    @Override
    public List<MyAccountPO> list() {
        String sql = "select * from my_account order by id desc";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MyAccountPO.class));
    }

    @Override
    public List<MyAccountPO> list(PageBO pageBO) {
        String sql = "select * from my_account order by id desc limit :limit offset :offset";
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("limit", pageBO.getPageSize());
        paramMap.put("offset", pageBO.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(MyAccountPO.class));
    }

    @Override
    public List<MyAccountPO> listByAppName(String appName, PageBO pageBO) {
        String sql = "select * from my_account where app_name like :appName order by id desc limit :limit offset :offset";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appName", "%" + appName + "%");
        paramMap.put("limit", pageBO.getPageSize());
        paramMap.put("offset", pageBO.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(MyAccountPO.class));
    }

    @Override
    public Integer count() {
        String sql = "select count(*) from my_account";
        return namedParameterJdbcTemplate.queryForObject(sql, EmptySqlParameterSource.INSTANCE, Integer.class);
    }

    @Override
    public Integer countByAppName(String appName) {
        String sql = "select count(*) from my_account where app_name like :appName";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appName", "%" + appName + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }
}
