package mjhct.accountmanager.domain.bo;

import java.util.List;

public class MyAccountListBO {

    Integer pageNumber;

    Integer pageSize;

    Integer totalPages;

    List<MyAccountInfoBO> list;

    public MyAccountListBO() {
    }

    public MyAccountListBO(Integer pageNumber, Integer pageSize, Integer totalPages, List<MyAccountInfoBO> list) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.list = list;
    }

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

    public List<MyAccountInfoBO> getList() {
        return list;
    }

    public void setList(List<MyAccountInfoBO> list) {
        this.list = list;
    }
}
