package mjhct.accountmanager.domain.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@Table("my_account")
public class MyAccountPO {

    private Integer id;

    private String appName;

    private String appUrl;

    private String myUsername;

    private String myPassword;

    private String remark;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;
}
