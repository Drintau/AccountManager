package drintau.accountmanager.shared.util;

public final class StrUtil {

    private StrUtil() {
        throw new UnsupportedOperationException("禁止实例化");
    }

    public static boolean isBlank(String str) {
        if (str == null) return true;
        return str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
