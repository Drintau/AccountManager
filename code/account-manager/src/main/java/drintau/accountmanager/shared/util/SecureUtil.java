package drintau.accountmanager.shared.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加解密工具类
 * 秘钥：aes秘钥字节数组->Base64编码
 * 加密：明文字节数组->aes加密->Base64编码
 * 解密：密文字符串->Base64解密->aes解密
 */
public final class SecureUtil {

    /**
     * 生成秘钥
     */
    public static String genSecureKey() throws Exception {
        byte[] key = AesUtil.genAesKey();
        return byteKeyToStringKey(key);
    }

    /**
     * 密钥字节数组转换为字符串
     */
    public static String byteKeyToStringKey(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * 密钥字符串转换为字节数组
     */
    public static byte[] stringKeyToByteKey(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 加密
     * @param key 密钥字节数组
     * @param input 明文
     * @return 密文
     */
    public static String encrypt(byte[] key, String input) throws Exception {
        // 明文转换为字节数组，加密得到密文字节数组
        byte[] output = AesUtil.aesEncrypt(key, input.getBytes(StandardCharsets.UTF_8));
        // Base64编码密文
        return Base64.getEncoder().encodeToString(output);
    }

    /**
     * 解密
     * @param key 密钥字节数组
     * @param input 密文
     * @return 明文
     */
    public static String decrypt(byte[] key, String input) throws Exception {
        // 密文Base64解码得到二进制字节数组,解密得到明文字节数组
        byte[] output = AesUtil.aesDecrypt(key, Base64.getDecoder().decode(input));
        // 明文字符串展示
        return new String(output, StandardCharsets.UTF_8);
    }

}
