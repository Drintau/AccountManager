package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.entity.bo.*;
import mjhct.accountmanager.entity.dto.*;
import mjhct.accountmanager.service.MyAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = new ArrayList<>(myAccountByIdOrAppName.size()*2);
        for (MyAccountInfoBO myAccountInfoBO : myAccountByIdOrAppName) {
            MyAccountQueryResDTO myAccountQueryResDTO = new MyAccountQueryResDTO();
            BeanUtils.copyProperties(myAccountInfoBO, myAccountQueryResDTO);
            myAccountQueryResDTOList.add(myAccountQueryResDTO);
        }
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResDTOList);
    }

    @PostMapping("/add")
    public CommonResult<MyAccountAddResDTO> add(@RequestBody @Validated MyAccountAddReqDTO myAccountAddReqDTO) {
        MyAccountAddBeforeBO myAccountAddBeforeBO = new MyAccountAddBeforeBO();
        BeanUtils.copyProperties(myAccountAddReqDTO, myAccountAddBeforeBO);
        MyAccountInfoBO myAccount = myAccountService.addMyAccount(myAccountAddBeforeBO);
        MyAccountAddResDTO myAccountAddResDTO = new MyAccountAddResDTO();
        BeanUtils.copyProperties(myAccount, myAccountAddResDTO);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
    }

    @GetMapping("/list")
    public CommonResult<List<MyAccountQueryResDTO>> list(@RequestParam(value = "decrypt", defaultValue = "false") Boolean decrypt) {
        List<MyAccountInfoBO> myAccounts = myAccountService.listMyAccount(decrypt);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = new ArrayList<>(myAccounts.size()*2);
        for (MyAccountInfoBO myAccountInfoBO : myAccounts) {
            MyAccountQueryResDTO myAccountQueryResDTO = new MyAccountQueryResDTO();
            BeanUtils.copyProperties(myAccountInfoBO, myAccountQueryResDTO);
            myAccountQueryResDTOList.add(myAccountQueryResDTO);
        }
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResDTOList);
    }

    @PostMapping("/update")
    public CommonResult<MyAccountUpdateResDTO> update(@RequestBody @Validated MyAccountUpdateReqDTO myAccountUpdateReqDTO) {
        MyAccountUpdateBeforeBO myAccountUpdateBO = new MyAccountUpdateBeforeBO();
        BeanUtils.copyProperties(myAccountUpdateReqDTO, myAccountUpdateBO);
        MyAccountInfoBO myAccount = myAccountService.updateMyAccount(myAccountUpdateBO);
        MyAccountUpdateResDTO myAccountUpdateResDTO = new MyAccountUpdateResDTO();
        BeanUtils.copyProperties(myAccount, myAccountUpdateResDTO);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqDTO myAccountDeleteReqDTO) {
        myAccountService.deleteMyAccount(myAccountDeleteReqDTO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

}
