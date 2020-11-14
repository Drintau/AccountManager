package mjhct.accountmanager.service;

/**
 * 加密服务接口
 */
public interface EncryptService {

    /**
     * 加密
     * @param plaintext 明文
     * @return 密文
     */
    String encrypt(String plaintext);

}
