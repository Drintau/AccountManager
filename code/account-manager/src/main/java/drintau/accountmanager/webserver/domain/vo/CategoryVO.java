package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryVO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category_name")
    private String categoryName;

}
