package drintau.accountmanager.shared.util;

/**
 * 分页工具类
 */
public final class PageUtil {

    /**
     * 计算总页数
     */
    public static int calcPages(int total, int pageSize) {
        if (total % pageSize == 0) {
            return total / pageSize;
        }
        return total / pageSize + 1;
    }

}
