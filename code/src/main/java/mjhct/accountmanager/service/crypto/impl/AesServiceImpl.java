package mjhct.accountmanager.service.crypto.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.config.SecurityConfig;
import mjhct.accountmanager.exception.CommonException;
import mjhct.accountmanager.service.crypto.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * AES对称加密服务
 */
@Service("aesService")
public class AesServiceImpl implements CryptoService {

    private static final Logger logger = LoggerFactory.getLogger(AesServiceImpl.class);

    @Resource
    private SecurityConfig securityConfig;

    @Override
    public String decrypt(@NotBlank String ciphertext) {
        try {
            // 使用配置的密钥
            byte[] key = securityConfig.getSecurityKey().getBytes();
            // 构建
            AES aes = SecureUtil.aes(key);
            // 解密为字符串
            return aes.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            logger.error("aes解密失败", e);
            throw new CommonException(CommonCode.CRYPTO_ERROR, "aes解密失败，请检查秘钥");
        }
    }

    @Override
    public String encrypt(@NotBlank String plaintext) {
        try {
            // 使用配置的密钥
            byte[] key = securityConfig.getSecurityKey().getBytes();
            // 构建
            AES aes = SecureUtil.aes(key);
            // 加密为16进制表示
            return aes.encryptHex(plaintext);
        } catch (Exception e) {
            logger.error("aes加密失败", e);
            throw new CommonException(CommonCode.CRYPTO_ERROR, "aes加密失败，请检查秘钥");
        }
    }
}
