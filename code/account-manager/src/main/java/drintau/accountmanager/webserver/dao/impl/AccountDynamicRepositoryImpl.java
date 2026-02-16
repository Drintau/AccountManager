package drintau.accountmanager.webserver.dao.impl;

import drintau.accountmanager.shared.util.NumberUtil;
import drintau.accountmanager.shared.util.StrUtil;
import drintau.accountmanager.webserver.dao.AccountDynamicRepository;
import drintau.accountmanager.webserver.domain.bo.AccountFindConditionBO;
import drintau.accountmanager.webserver.domain.po.AccountPO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Account 动态 SQL
 */
@RequiredArgsConstructor
@Repository
public class AccountDynamicRepositoryImpl implements AccountDynamicRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<AccountPO> findByCondition(AccountFindConditionBO conditionBO) {
        StringBuilder sql = new StringBuilder("SELECT * FROM account ");
        Map<String, Object> paramMap = new HashMap<>();

        buildWhereSql(conditionBO, sql, paramMap);

        // 排序
        sql.append(" ORDER BY id desc ");

        // 分页
        if (NumberUtil.isNotNullAndGreaterThanZero(conditionBO.getPageNum()) && NumberUtil.isNotNullAndGreaterThanZero(conditionBO.getPageSize())) {
            sql.append(" LIMIT :limit OFFSET :offset ");
            paramMap.put("limit", conditionBO.getPageSize());
            paramMap.put("offset", conditionBO.getOffset());
        }

        return jdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<>(AccountPO.class));
    }

    @Override
    public Integer countByCondition(AccountFindConditionBO conditionBO) {
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM account ");
        Map<String, Object> paramMap = new HashMap<>();

        buildWhereSql(conditionBO, sql, paramMap);

        return jdbcTemplate.queryForObject(sql.toString(), paramMap, Integer.class);
    }

    private void buildWhereSql(AccountFindConditionBO conditionBO, StringBuilder sql, Map<String, Object> paramMap) {
        // 动态条件
        if (NumberUtil.isNotNullAndGreaterThanZero(conditionBO.getCategoryId()) || StrUtil.isNotBlank(conditionBO.getKeywordAppName())) {
            sql.append(" WHERE ");

            boolean flag = false;

            if (NumberUtil.isNotNullAndGreaterThanZero(conditionBO.getCategoryId())) {
                sql.append(" category_id = :categoryId ");
                paramMap.put("categoryId", conditionBO.getCategoryId());
                flag = true;
            }

            if (StrUtil.isNotBlank(conditionBO.getKeywordAppName())) {
                if (flag) {
                    sql.append(" AND ");
                }
                sql.append(" app_name LIKE :keywordAppName ");
                paramMap.put("keywordAppName", "%" + conditionBO.getKeywordAppName() + "%");
            }

        }
    }

}
