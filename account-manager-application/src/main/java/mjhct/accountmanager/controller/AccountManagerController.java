package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.domain.bo.*;
import mjhct.accountmanager.domain.dto.*;
import mjhct.accountmanager.exception.BusinessException;
import mjhct.accountmanager.service.myaccount.MyAccountService;
import mjhct.accountmanager.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private MyAccountService myAccountService;

    @PostMapping("/query")
    public CommonResult<List<MyAccountQueryResDTO>> query(@RequestBody @Validated MyAccountQueryReqDTO reqDTO) {
        MyAccountQueryConditionBO condition = BeanUtil.copy(reqDTO, MyAccountQueryConditionBO.class);
        List<MyAccountInfoBO> myAccountByCondition = myAccountService.queryMyAccount(condition);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountByCondition, MyAccountQueryResDTO.class);
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
    public CommonResult<MyAccountListResDTO> list(@RequestParam(value = "decrypt", defaultValue = "false") Boolean decrypt,
                                                  @RequestParam(value = "page_number", defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        // 前端页面看到的第1页，分页参数是第0页
        if (pageNumber < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "请求页数不能小于1");
        }
        if (pageSize < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "每页数据不能小于1");
        }
        MyAccountListBO myAccountListBO = myAccountService.listMyAccount(decrypt, pageNumber - 1, pageSize);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountListBO.getList(), MyAccountQueryResDTO.class);
        MyAccountListResDTO myAccountListResDTO = new MyAccountListResDTO();
        myAccountListResDTO.setPageNumber(pageNumber);
        myAccountListResDTO.setPageSize(pageSize);
        myAccountListResDTO.setTotalPages(myAccountListBO.getTotalPages());
        myAccountListResDTO.setList(myAccountQueryResDTOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResDTO);
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

    @PostMapping("/export")
    public void export() {
        myAccountService.export();
    }

}
