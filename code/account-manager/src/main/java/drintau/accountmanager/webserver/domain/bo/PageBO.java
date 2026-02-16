package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;

/**
 * 分页信息对象
 * 规定：只能被继承，抽象类不存储数据
 */
@Data
public abstract class PageBO {

    /**
     * 当前页码，即 前端页码，从 1 开始
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据总条数
     */
    private Integer total;

    protected PageBO() {}

    protected PageBO(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 偏移量 = (pageNum - 1) * pageSize
     * 数据库分页用的
     */
    public Integer getOffset() {
        int pn = (pageNum == null ? 1 : pageNum);
        int ps = (pageSize == null ? 10 : pageSize);
        return (pn - 1) * ps;
    }

}
