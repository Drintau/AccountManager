package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.config.SettingConfig;
import mjhct.accountmanager.service.myaccount.MyPasswordService;
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

    @Resource(name = "myPasswordService")
    private MyPasswordService myPasswordService;

    @Resource
    private SettingConfig settingConfig;

    @GetMapping("/get")
    public CommonResult<String> getRandomPassword() {
        String randomPassword = myPasswordService.getRandomPassword(settingConfig.getPasswordDigits());
        return new CommonResult<>(CommonCode.SUCCESS, CommonCode.SUCCESS.message, randomPassword);
    }

}
