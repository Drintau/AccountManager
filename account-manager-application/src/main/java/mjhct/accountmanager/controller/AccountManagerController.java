package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @GetMapping("/test")
    public CommonResult<String> test() {
        logger.debug("{}", "测试");
        return new CommonResult<>(CommonCode.SUCCESS);
    }

}
