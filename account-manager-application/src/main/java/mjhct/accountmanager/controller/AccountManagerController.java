package mjhct.accountmanager.controller;

import mjhct.accountmanager.bo.MyAccountAddBO;
import mjhct.accountmanager.bo.MyAccountUpdateBO;
import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.dto.MyAccountAddReqDTO;
import mjhct.accountmanager.dto.MyAccountDeleteReqDTO;
import mjhct.accountmanager.dto.MyAccountQueryResDTO;
import mjhct.accountmanager.dto.MyAccountUpdateReqDTO;
import mjhct.accountmanager.entity.MyAccount;
import mjhct.accountmanager.service.DataBaseService;
import mjhct.accountmanager.service.MyAccountService;
import mjhct.accountmanager.service.crypto.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

    @Resource
    private MyAccountService myAccountService;

    @Resource
    private DataBaseService dataBaseService;

    @GetMapping("/query")
    public CommonResult<List<MyAccountQueryResDTO>> query(@RequestParam(name = "id", required = false) Integer id,
                                               @RequestParam(name = "app_name", required = false) String appName) {

        if (id == null && StringUtils.isEmpty(appName)) {
            return new CommonResult<>(CommonCode.REQUEST_PARAMETER_ERROR, "id和app_name不能同时为空");
        }

        List<MyAccountQueryResDTO> myAccountByIdOrAppName = myAccountService.getMyAccountByIdOrAppName(id, appName);

        return new CommonResult<>(CommonCode.SUCCESS, myAccountByIdOrAppName);
    }

    @PostMapping("/add")
    public CommonResult<MyAccount> add(@RequestBody @Validated MyAccountAddReqDTO myAccountAddReqDTO) {
        MyAccountAddBO myAccountAddBO = new MyAccountAddBO();
        BeanUtils.copyProperties(myAccountAddReqDTO, myAccountAddBO);
        logger.debug("要添加的账号是{}", myAccountAddBO);
        MyAccount myAccount = myAccountService.addMyAccount(myAccountAddBO);
        return new CommonResult<>(CommonCode.SUCCESS, myAccount);
    }

    @GetMapping("/list")
    public CommonResult<List<MyAccountQueryResDTO>> list() {
        List<MyAccountQueryResDTO> myAccounts = myAccountService.listMyAccount();
        return new CommonResult<>(CommonCode.SUCCESS, myAccounts);
    }

    @PostMapping("/update")
    public CommonResult<MyAccount> update(@RequestBody @Validated MyAccountUpdateReqDTO myAccountUpdateReqDTO) {
        MyAccountUpdateBO myAccountUpdateBO = new MyAccountUpdateBO();
        BeanUtils.copyProperties(myAccountUpdateReqDTO, myAccountUpdateBO);
        logger.debug("要修改的账号是{}", myAccountUpdateBO);
        MyAccount myAccount = myAccountService.updateMyAccount(myAccountUpdateBO);
        return new CommonResult<>(CommonCode.SUCCESS, myAccount);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqDTO myAccountDeleteReqDTO) {
        myAccountService.deleteMyAccount(myAccountDeleteReqDTO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

    @PostMapping("init")
    public CommonResult<String> init(){
        try {
            dataBaseService.initTable();
        } catch (SQLException e) {
            logger.error("初始化数据库失败", e);
            return new CommonResult<>(CommonCode.FAIL, "初始化数据库失败，请尝试删除数据库文件重试");
        }
        return new CommonResult<>(CommonCode.SUCCESS, "初始化数据库成功!");
    }

}
