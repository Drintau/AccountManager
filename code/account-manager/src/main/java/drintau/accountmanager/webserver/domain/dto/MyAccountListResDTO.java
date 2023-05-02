package drintau.accountmanager.webserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MyAccountListResDTO {

    @JsonProperty("page_number")
    Integer pageNumber;

    @JsonProperty("page_size")
    Integer pageSize;

    @JsonProperty("total_pages")
    Integer totalPages;

    @JsonProperty("list")
    List<MyAccountQueryResDTO> list;
}
