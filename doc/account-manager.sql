-- H2database建表语句
CREATE TABLE IF NOT EXISTS my_account(
    id INT IDENTITY COMMENT '逻辑主键',
    app_name VARCHAR(20) NOT NULL COMMENT '应用名称',
    app_url VARCHAR(100) COMMENT '网址',
    my_username VARCHAR(200) NOT NULL COMMENT '加密用户名',
    my_password VARCHAR(200) NOT NULL COMMENT '加密密码',
    remark VARCHAR(200) COMMENT '备注',
    create_time TIMESTAMP(3) WITH TIME ZONE COMMENT '创建时间',
    update_time TIMESTAMP(3) WITH TIME ZONE COMMENT '更新时间'
);