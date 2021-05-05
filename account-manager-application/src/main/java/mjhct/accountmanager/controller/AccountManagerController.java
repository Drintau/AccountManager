package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.domain.bo.MyAccountAddBeforeBO;
import mjhct.accountmanager.domain.bo.MyAccountInfoBO;
import mjhct.accountmanager.domain.bo.MyAccountQueryConditionBO;
import mjhct.accountmanager.domain.bo.MyAccountUpdateBeforeBO;
import mjhct.accountmanager.domain.dto.*;
import mjhct.accountmanager.service.myaccount.impl.MyAccountServiceImpl;
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

    @Resource(name = "myAccountService")
    private MyAccountServiceImpl myAccountService;

    @GetMapping("/query")
    public CommonResult<List<MyAccountQueryResDTO>> query(@RequestBody @Validated MyAccountQueryReqDTO reqDTO) {
        MyAccountQueryConditionBO condition = BeanUtil.copy(reqDTO, MyAccountQueryConditionBO.class);
        List<MyAccountInfoBO> myAccountByCondition = myAccountService.queryMyAccount(condition);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountByCondition, MyAccountQueryResDTO::new);
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
