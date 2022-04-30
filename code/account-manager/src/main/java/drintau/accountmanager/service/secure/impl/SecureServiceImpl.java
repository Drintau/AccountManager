package drintau.accountmanager.service.secure.impl;

import drintau.accountmanager.commons.CommonCode;
import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.exception.CommonException;
import drintau.accountmanager.service.secure.SecureService;
import drintau.accountmanager.util.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 加解密服务
 */
@Service
public class SecureServiceImpl implements SecureService {

    private static final Logger logger = LoggerFactory.getLogger(SecureServiceImpl.class);

    @Resource
    private AccountManagerConfig accountManagerConfig;

    @Override
    public String decrypt(@NotBlank String ciphertext) {
        try {
            return SecureUtil.decrypt(accountManagerConfig.getSecurityKeyByteArray(), ciphertext);
        } catch (Exception e) {
            logger.error("解密失败", e);
            throw new CommonException(CommonCode.SYSTEM_ERROR, "解密失败，请检查秘钥");
        }
    }

    @Override
    public String encrypt(@NotBlank String plaintext) {
        try {
            return SecureUtil.encrypt(accountManagerConfig.getSecurityKeyByteArray(), plaintext);
        } catch (Exception e) {
            logger.error("加密失败", e);
            throw new CommonException(CommonCode.SYSTEM_ERROR, "加密失败，请检查秘钥");
        }
    }
}
