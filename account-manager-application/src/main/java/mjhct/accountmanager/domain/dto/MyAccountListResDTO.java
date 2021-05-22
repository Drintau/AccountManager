package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MyAccountListResDTO {

    @JsonProperty("page_number")
    Integer pageNumber;

    @JsonProperty("page_size")
    Integer pageSize;

    @JsonProperty("total_pages")
    Integer totalPages;

    @JsonProperty("list")
    List<MyAccountQueryResDTO> list;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MyAccountQueryResDTO> getList() {
        return list;
    }

    public void setList(List<MyAccountQueryResDTO> list) {
        this.list = list;
    }
}
