CREATE TABLE IF NOT EXISTS config (
    id INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '逻辑主键',
    config_key VARCHAR(50) NOT NULL UNIQUE COMMENT '配置项的唯一键',
    config_value VARCHAR(200) NOT NULL COMMENT '配置项的值',
    remark VARCHAR(200) COMMENT '备注信息'
);

INSERT INTO config (config_key, config_value, remark)
SELECT 'password-length', '10', '密码长度'
    WHERE NOT EXISTS (
    SELECT 1 FROM config WHERE config_key = 'password-length'
);

CREATE TABLE IF NOT EXISTS category (
    id INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '逻辑主键',
    category_name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类的名称'
);

CREATE TABLE IF NOT EXISTS account (
    id INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '逻辑主键',
    category_id INTEGER COMMENT '所属分类ID',
    app_name VARCHAR(50) NOT NULL COMMENT '应用名称',
    app_url VARCHAR(200) COMMENT '应用网址',
    username VARCHAR(200) NOT NULL COMMENT '加密后的用户名',
    password VARCHAR(200) NOT NULL COMMENT '加密后的密码',
    remark VARCHAR(200) COMMENT '备注信息',
    create_time BIGINT NOT NULL COMMENT '创建时间（UTC秒级时间戳）',
    update_time BIGINT NOT NULL COMMENT '修改时间（UTC秒级时间戳）'
);

CREATE INDEX IF NOT EXISTS idx_account_app_name ON account(app_name);
CREATE INDEX IF NOT EXISTS idx_account_category_id_app_name ON account(category_id, app_name);
