package drintau.accountmanager.shared.util;

/**
 * 分页工具类
 */
public final class PageUtil {

    /**
     * 计算总页数
     */
    public static int calcPages(Integer total, Integer pageSize) {
        if (total == null || pageSize == null) {
            return 0;
        }
        if (total % pageSize == 0) {
            return total / pageSize;
        }
        return total / pageSize + 1;
    }

}
