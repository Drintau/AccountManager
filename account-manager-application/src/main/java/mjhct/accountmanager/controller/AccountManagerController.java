package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.entity.bo.MyAccountAddBeforeBO;
import mjhct.accountmanager.entity.bo.MyAccountInfoBO;
import mjhct.accountmanager.entity.bo.MyAccountUpdateBeforeBO;
import mjhct.accountmanager.entity.dto.*;
import mjhct.accountmanager.service.MyAccountService;
import mjhct.accountmanager.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountManagerController {

    public static final Logger logger = LoggerFactory.getLogger(AccountManagerController.class);

    @Resource
    private MyAccountService myAccountService;

    @GetMapping("/query")
    public CommonResult<List<MyAccountQueryResDTO>> query(@RequestParam(name = "id", required = false) Integer id,
                                               @RequestParam(name = "app_name", required = false) String appName) {
        if (id == null && StringUtils.isEmpty(appName)) {
            return new CommonResult<>(CommonCode.REQUEST_PARAMETER_ERROR, "id和app_name不能同时为空");
        }
        List<MyAccountInfoBO> myAccountByIdOrAppName = myAccountService.getMyAccountByIdOrAppName(id, appName);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountByIdOrAppName, MyAccountQueryResDTO::new);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResDTOList);
    }

    @PostMapping("/add")
    public CommonResult<MyAccountAddResDTO> add(@RequestBody @Validated MyAccountAddReqDTO myAccountAddReqDTO) {
        MyAccountAddBeforeBO myAccountAddBeforeBO = BeanUtil.copy(myAccountAddReqDTO, MyAccountAddBeforeBO.class);
        MyAccountInfoBO myAccount = myAccountService.addMyAccount(myAccountAddBeforeBO);
        MyAccountAddResDTO myAccountAddResDTO = BeanUtil.copy(myAccount, MyAccountAddResDTO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
    }

    @GetMapping("/list")
    public CommonResult<List<MyAccountQueryResDTO>> list(@RequestParam(value = "decrypt", defaultValue = "false") Boolean decrypt) {
        List<MyAccountInfoBO> myAccounts = myAccountService.listMyAccount(decrypt);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccounts, MyAccountQueryResDTO::new);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResDTOList);
    }

    @PostMapping("/update")
    public CommonResult<MyAccountUpdateResDTO> update(@RequestBody @Validated MyAccountUpdateReqDTO myAccountUpdateReqDTO) {
        MyAccountUpdateBeforeBO myAccountUpdateBO = BeanUtil.copy(myAccountUpdateReqDTO, MyAccountUpdateBeforeBO.class);
        MyAccountInfoBO myAccount = myAccountService.updateMyAccount(myAccountUpdateBO);
        MyAccountUpdateResDTO myAccountUpdateResDTO = BeanUtil.copy(myAccount, MyAccountUpdateResDTO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqDTO myAccountDeleteReqDTO) {
        myAccountService.deleteMyAccount(myAccountDeleteReqDTO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

}
