package mjhct.accountmanager.service.crypto;

/**
 * 加解密服务接口
 * crypto密码学
 */
public interface CryptoService {

    /**
     * 解密
     * @param ciphertext 密文
     * @return plaintext 原文
     */
    String decrypt(String ciphertext);

    /**
     * 加密
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    String encrypt(String plaintext);

}
