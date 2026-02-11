package drintau.accountmanager.webserver.domain.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("config")
public class ConfigPO {

    @Id
    @Column("id")
    private Integer id;

    @Column("config_key")
    private String configKey;

    @Column("config_value")
    private String configValue;

    @Column("remark")
    private String remark;

}
