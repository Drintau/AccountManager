package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.CommonCode;
import drintau.accountmanager.commons.CommonResult;
import drintau.accountmanager.webserver.config.AccountManagerConfig;
import drintau.accountmanager.webserver.service.myaccount.MyPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/password")
@CrossOrigin
@Slf4j
public class PasswordController {

    @Resource
    private MyPasswordService myPasswordService;

    @Resource
    private AccountManagerConfig accountManagerConfig;

    @GetMapping("/get")
    public CommonResult<String> getRandomPassword() {
        String randomPassword = myPasswordService.getRandomPassword(accountManagerConfig.getPasswordDigits());
        return new CommonResult<>(CommonCode.SUCCESS, CommonCode.SUCCESS.message, randomPassword);
    }

}
