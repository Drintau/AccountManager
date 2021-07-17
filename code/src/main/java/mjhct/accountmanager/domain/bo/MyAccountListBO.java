package mjhct.accountmanager.domain.bo;

import java.util.List;

public class MyAccountListBO {

    /**
     * 偏移页数
     */
    Integer offsetPageNumber;

    /**
     * 每页条数
     */
    Integer pageSize;

    /**
     * 总页数
     */
    Integer totalPages;

    /**
     * 当前页的记录
     */
    List<MyAccountInfoBO> list;

    public MyAccountListBO() {
    }

    public MyAccountListBO(Integer offsetPageNumber, Integer pageSize,Integer totalPages, List<MyAccountInfoBO> list) {
        this.offsetPageNumber = offsetPageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.list = list;
    }

    public Integer getOffsetPageNumber() {
        return offsetPageNumber;
    }

    public void setOffsetPageNumber(Integer offsetPageNumber) {
        this.offsetPageNumber = offsetPageNumber;
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
