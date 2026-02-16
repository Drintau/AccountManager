package drintau.accountmanager.shared.util;

public final class NumberUtil {

    /**
     * 一个整数非空且大于0
     */
    public static boolean isNotNullAndGreaterThanZero(Number checkNumber) {
        return checkNumber != null && checkNumber.doubleValue() > 0;
    }

}
