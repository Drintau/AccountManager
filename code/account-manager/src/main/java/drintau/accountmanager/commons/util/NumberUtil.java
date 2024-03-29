package drintau.accountmanager.commons.util;

public class NumberUtil {

    /**
     * 一个整数非空且大于0
     * @param checkNumber 校验的整数
     * @return
     */
    public static boolean isNotNullAndGreaterThanZero(Number checkNumber) {
        return checkNumber != null && checkNumber.doubleValue() > 0;
    }

}
