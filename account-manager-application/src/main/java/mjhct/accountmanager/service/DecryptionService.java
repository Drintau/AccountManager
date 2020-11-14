package mjhct.accountmanager.service;

/**
 * 解密服务接口
 */
public interface DecryptionService {

    /**
     * 解密
     * @param ciphertext 密文
     * @return 原文
     */
    String decrypt(String ciphertext);

}
