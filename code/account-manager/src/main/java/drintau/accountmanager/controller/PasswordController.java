package drintau.accountmanager.controller;

import drintau.accountmanager.commons.CommonCode;
import drintau.accountmanager.commons.CommonResult;
import drintau.accountmanager.config.AccountManagerConfig;
import drintau.accountmanager.service.myaccount.MyPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/password")
@CrossOrigin
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

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
