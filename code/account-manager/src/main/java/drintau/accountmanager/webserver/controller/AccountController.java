package drintau.accountmanager.webserver.controller;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.read.listener.PageReadListener;
import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.exception.CommonException;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.commons.util.NumberUtil;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.AccountIEBO;
import drintau.accountmanager.webserver.domain.bo.AccountListBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindBO;
import drintau.accountmanager.webserver.domain.vo.*;
import drintau.accountmanager.webserver.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
@Slf4j
public class AccountController {

    @Resource(name = "myAccountService")
    private AccountService accountService;

    @GetMapping("/get")
    public CommonResult<MyAccountQueryResVO> get(@RequestParam(value = "id") Integer id) {
        if (!NumberUtil.isNotNullAndGreaterThanZero(id)) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "查询id非法！");
        }
        AccountBO myAccountById = accountService.getMyAccountById(id);
        if (myAccountById == null) {
            throw new BusinessException(CommonCode.FAIL, "查询数据不存在，请刷新页面！");
        }
        MyAccountQueryResVO myAccountQueryResVO = BeanUtil.copy(myAccountById, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResVO);
    }

    @PostMapping("/query")
    public CommonResult<MyAccountListResVO> query(@RequestBody @Validated MyAccountQueryReqVO reqDTO) {
        AccountFindBO condition = BeanUtil.copy(reqDTO, AccountFindBO.class);
        AccountListBO accountListBO = accountService.queryMyAccount(condition);
        List<MyAccountQueryResVO> myAccountQueryResVOList = BeanUtil.copyList(accountListBO.getList(), MyAccountQueryResVO.class);
        MyAccountListResVO myAccountListResVO = new MyAccountListResVO();
        myAccountListResVO.setPageNumber(reqDTO.getPageNumber());
        myAccountListResVO.setPageSize(reqDTO.getPageSize());
        myAccountListResVO.setTotalPages(accountListBO.getTotalPages());
        myAccountListResVO.setTotalRecords(accountListBO.getTotalRecords());
        myAccountListResVO.setList(myAccountQueryResVOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResVO);
    }

    @PostMapping("/add")
    public CommonResult<MyAccountQueryResVO> add(@RequestBody @Validated MyAccountAddReqVO myAccountAddReqVO) {
        AccountBO myAccountAddBeforeBO = BeanUtil.copy(myAccountAddReqVO, AccountBO.class);
        AccountBO myAccount = accountService.addMyAccount(myAccountAddBeforeBO);
        MyAccountQueryResVO myAccountAddResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
    }

    @PostMapping("/update")
    public CommonResult<MyAccountQueryResVO> update(@RequestBody @Validated MyAccountUpdateReqVO myAccountUpdateReqVO) {
        AccountBO myAccountUpdateBO = BeanUtil.copy(myAccountUpdateReqVO, AccountBO.class);
        AccountBO myAccount = accountService.updateMyAccount(myAccountUpdateBO);
        MyAccountQueryResVO myAccountUpdateResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqVO myAccountDeleteReqVO) {
        accountService.deleteMyAccount(myAccountDeleteReqVO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=exportdata.xlsx");

        try {
            EasyExcel.write(response.getOutputStream(), AccountIEBO.class)
                    .sheet("数据")
                    .doWrite(() -> accountService.exportMyAccounts());
        } catch (IOException e) {
            throw new CommonException(CommonCode.FAIL, "响应文件数据失败!");
        }
    }

    @PostMapping("/import")
    public CommonResult importAccounts(@RequestParam("file") MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), AccountIEBO.class,
                    new PageReadListener<AccountIEBO>(dataList -> accountService.importMyAccounts(dataList))
            ).sheet().doRead();
        } catch (Exception e) {
            log.error("导入数据失败！", e);
            return new CommonResult(CommonCode.FAIL, "导入数据失败！建议删除数据库文件重试。");
        }
        return new CommonResult(CommonCode.SUCCESS);
    }

}
