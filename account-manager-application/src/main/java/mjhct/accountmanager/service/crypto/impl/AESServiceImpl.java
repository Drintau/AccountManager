package mjhct.accountmanager.service.crypto.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import mjhct.accountmanager.config.AESConfig;
import mjhct.accountmanager.service.crypto.CryptoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * AES对称加密服务
 */
@Service("aesService")
public class AESServiceImpl implements CryptoService {

    @Resource
    private AESConfig aesConfig;

    @Override
    public String decrypt(String ciphertext) {
        // 使用配置的密钥
        byte[] key = aesConfig.getKey().getBytes();
        // 构建
        AES aes = SecureUtil.aes(key);
        // 解密为字符串
        return aes.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
    }

    @Override
    public String encrypt(String plaintext) {
        // 使用配置的密钥
        byte[] key = aesConfig.getKey().getBytes();
        // 构建
        AES aes = SecureUtil.aes(key);
        // 加密为16进制表示
        return aes.encryptHex(plaintext);
    }
}
