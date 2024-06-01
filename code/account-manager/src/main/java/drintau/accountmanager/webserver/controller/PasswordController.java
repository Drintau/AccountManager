package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.webserver.config.WebServerConfig;
import drintau.accountmanager.webserver.service.PasswordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@CrossOrigin
@Slf4j
public class PasswordController {

    @Resource
    private PasswordService passwordService;

    @Resource
    private WebServerConfig webServerConfig;

    @GetMapping("/get")
    public CommonResult<String> getRandomPassword() {
        String randomPassword = passwordService.getRandomPassword(webServerConfig.getPasswordDigits());
        return new CommonResult<>(CommonCode.SUCCESS, CommonCode.SUCCESS.message, randomPassword);
    }

}
