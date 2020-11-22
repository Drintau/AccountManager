package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.entity.MyAccount;
import mjhct.accountmanager.service.MyAccountService;
import mjhct.accountmanager.service.crypto.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Random;


@RestController
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

    @Resource
    private MyAccountService myAccountService;

    @GetMapping("/testQ")
    public CommonResult<MyAccount> testQ(@RequestParam("id") Integer id) throws SQLException {
        logger.debug("测试成功");

        MyAccount myAccountById = myAccountService.getMyAccountById(id);

        return new CommonResult<>(CommonCode.SUCCESS, myAccountById);
    }

    @PostMapping("/testI")
    public CommonResult<MyAccount> testI() {
        logger.debug("测试成功");

        MyAccount myAccount = new MyAccount();
        myAccount.setAppName("测试" + new Random().nextInt(Integer.MAX_VALUE));
        myAccount.setUrl("xxxx");
        myAccount.setMyUsername("xxxx");
        myAccount.setMyPassword("xxxxx");
        myAccount.setRemark("xxxxx");

        logger.debug("新增数据:{}", myAccount);

        MyAccount myAccount1 = myAccountService.saveMyAccount(myAccount);

        logger.debug("新增结果:{}", myAccount1);

        return new CommonResult<>(CommonCode.SUCCESS, myAccount1);
    }

    @GetMapping("/testL")
    public CommonResult<Iterable<MyAccount>> testL() {
        logger.debug("测试成功");

        Iterable<MyAccount> myAccounts = myAccountService.listMyAccount();

        return new CommonResult<>(CommonCode.SUCCESS, myAccounts);
    }

}
