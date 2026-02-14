package drintau.accountmanager.shared.util;

/**
 * 分页工具类
 */
public class PageUtil {

    /**
     * 计算总页数
     * @param totalRecords
     * @param pageSize
     * @return
     */
    public static int calcTotalPages(int totalRecords, int pageSize) {
        if (totalRecords % pageSize == 0) {
            return totalRecords / pageSize;
        }
        return totalRecords / pageSize + 1;
    }

}
