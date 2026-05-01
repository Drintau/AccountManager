package drintau.accountmanager.shared.util;

import java.security.SecureRandom;

public final class RandomUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

    private RandomUtil() {
        throw new UnsupportedOperationException("禁止实例化");
    }

    /**
     * 生成 [0, maxInt) 之间的随机整数
     * 0 <= x < maxInt
     */
    public static int randomInt(int maxInt) {
        return secureRandom.nextInt(maxInt);
    }

}
