package drintau.accountmanager.webserver.domain.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("category")
public class CategoryPO {

    @Id
    @Column("id")
    private Integer id;

    @Column("category_name")
    private String categoryName;

}
