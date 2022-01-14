package mjhct.accountmanager.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("my_account")
public class MyAccountPO {

    @Id
    @Column("id")
    private Integer id;

    @Column("app_name")
    private String appName;

    @Column("app_url")
    private String appUrl;

    @Column("my_username")
    private String myUsername;

    @Column("my_password")
    private String myPassword;

    @Column("remark")
    private String remark;

    @Column("create_time")
    private LocalDateTime createTime;

    @Column("update_time")
    private LocalDateTime updateTime;
}
