package mjhct.accountmanager.domain.bo;

import lombok.Data;

/**
 * 分页信息对象
 * 只能被继承，抽象类没有存储数据的属性
 */
@Data
public abstract class PageBO {

    /**
     * 页码
     * 人看到的，前端页码
     */
    private Integer pageNumber;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 总记录数
     */
    private Integer totalRecords;


    /**
     * 偏移量页数 = 前端页码 - 1
     * 数据库分页用的
     */
    private Integer getOffsetPageNumber() {
        return pageNumber - 1;
    }

    /**
     * 偏移量 = (pageNumber - 1) * pageSize
     * 数据库分页用的
     * @return
     */
    public Integer getOffset() {
        return getOffsetPageNumber() * pageSize;
    }

    public PageBO() {
    }

    public PageBO(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
