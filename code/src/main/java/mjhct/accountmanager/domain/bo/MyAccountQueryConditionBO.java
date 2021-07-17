package mjhct.accountmanager.domain.bo;

/**
 * 查询条件
 */
public class MyAccountQueryConditionBO {

    /**
     * 前端页码
     */
    private Integer pageNumber;

    /**
     * 每页条数
     */
    private Integer pageSize;

    private String fuzzyName;

    private Boolean decrypt;

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public Boolean getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Boolean decrypt) {
        this.decrypt = decrypt;
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

    /**
     * 偏移页数 = 前端页码 - 1
     */
    public Integer getOffsetPageNumber() {
        return pageNumber - 1;
    }
}
