package drintau.accountmanager.webserver.domain.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("account")
public class AccountPO {

    @Id
    @Column("id")
    private Integer id;

    @Column("category_id")
    private Integer categoryId;

    @Column("app_name")
    private String appName;

    @Column("app_url")
    private String appUrl;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("remark")
    private String remark;

    @Column("create_time")
    private Long createTime;

    @Column("update_time")
    private Long updateTime;

}
