package mjhct.accountmanager.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 加解密工具类
 */
public class SecureUtil {

    public static void main(String[] args) throws Exception {
        // 原文:
        String message = "Hello, world!";
        System.out.println("Message: " + message);
        // 128位密钥 = 16 bytes Key:
        byte[] key = genAesSecret();
        System.out.println("秘钥Base64编码：" + Base64.getEncoder().encodeToString(key));
        // 加密:
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = aesEncrypt(key, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        // 解密:
        byte[] decrypted = aesDecrypt(key, encrypted);
        System.out.println(decrypted);
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));
    }

    /**
     * 生成随机AES秘钥
     * @return
     * @throws Exception
     */
    public static byte[] genAesSecret() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * AES加密
     * @param key
     * @param input
     * @return
     */
    public static byte[] aesEncrypt(byte[] key, byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    /**
     * AES解密
     * @param key
     * @param input
     * @return
     */
    public static byte[] aesDecrypt(byte[] key, byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

}
