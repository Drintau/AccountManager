package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.exception.CommonException;
import drintau.accountmanager.commons.util.SecureUtil;
import drintau.accountmanager.webserver.config.WebServerConfig;
import drintau.accountmanager.webserver.service.SecureService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 加解密服务
 */
@Service
@Slf4j
public class SecureServiceImpl implements SecureService {

    @Resource
    private WebServerConfig webServerConfig;

    @Override
    public String decrypt(@NotBlank String ciphertext) {
        try {
            return SecureUtil.decrypt(webServerConfig.getSecurityKeyByteArray(), ciphertext);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new CommonException(CommonCode.SYSTEM_ERROR, "解密失败，请检查秘钥");
        }
    }

    @Override
    public String encrypt(@NotBlank String plaintext) {
        try {
            return SecureUtil.encrypt(webServerConfig.getSecurityKeyByteArray(), plaintext);
        } catch (Exception e) {
            log.error("加密失败", e);
            throw new CommonException(CommonCode.SYSTEM_ERROR, "加密失败，请检查秘钥");
        }
    }
}
