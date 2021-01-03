package mjhct.accountmanager.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * 加解密工具类
 */
public class CryptoUtil {

    /*=== AES ===*/

    /**
     * 生成随机AES秘钥
     * @return
     */
    public static String generateAESKey() {
        // hutool工具默认使用128位
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return HexUtil.encodeHexStr(key, false);
    }

}
