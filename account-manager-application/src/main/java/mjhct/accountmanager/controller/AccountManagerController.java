package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.service.impl.AESServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @Resource
    private AESServiceImpl aesService;

    @GetMapping("/test")
    public CommonResult<String> test() {
        String miwen = aesService.encrypt("testets");
        logger.info("密文:{}", miwen);
        String mingwen = aesService.decrypt(miwen);
        logger.info("明文:{}", mingwen);
        return new CommonResult<>(CommonCode.SUCCESS);
    }

}
