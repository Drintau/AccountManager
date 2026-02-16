package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountFindResVO {

    @JsonProperty("page_num")
    private Integer pageNum;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("pages")
    private Integer pages;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("list")
    private List<AccountVO> list;
}
