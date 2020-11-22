package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.service.crypto.CryptoService;
import mjhct.accountmanager.service.database.DataBaseInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;


@RestController
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

    @Resource
    private DataBaseInitService dataBaseInitService;

    @GetMapping("/test")
    public CommonResult<String> test() throws SQLException {
        logger.debug("测试成功");
        String encrypt = cryptoService.encrypt("123");
        logger.debug("密文:{}", encrypt);
        String decrypt = cryptoService.decrypt(encrypt);
        logger.debug("明文:{}", decrypt);
        //dataBaseInitService.initTable();
        return new CommonResult<>(CommonCode.SUCCESS);
    }

}
