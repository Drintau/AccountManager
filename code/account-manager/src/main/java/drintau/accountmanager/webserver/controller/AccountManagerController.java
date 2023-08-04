package drintau.accountmanager.webserver.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.webserver.domain.bo.*;
import drintau.accountmanager.webserver.domain.dto.*;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.exception.CommonException;
import drintau.accountmanager.webserver.service.AccountService;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.commons.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
@Slf4j
public class AccountManagerController {

    @Resource(name = "accountService")
    private AccountService accountService;

    /**
     * id 精确查询，且解密
     * @return
     */
    @GetMapping("/get")
    public CommonResult<MyAccountQueryResDTO> get(@RequestParam(value = "id") Integer id) {
        if (!NumberUtil.isNotNullAndGreaterThanZero(id)) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "查询id非法！");
        }
        MyAccountInfoBO myAccountById = accountService.getMyAccountById(id);
        if (myAccountById == null) {
            throw new BusinessException(CommonCode.FAIL, "查询数据不存在，请刷新页面！");
        }
        MyAccountQueryResDTO myAccountQueryResDTO = BeanUtil.copy(myAccountById, MyAccountQueryResDTO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResDTO);
    }

    /**
     * 条件查询，返回带分页
     * @param reqDTO
     * @return
     */
    @PostMapping("/query")
    public CommonResult<MyAccountListResDTO> query(@RequestBody @Validated MyAccountQueryReqDTO reqDTO) {
        MyAccountQueryConditionBO condition = BeanUtil.copy(reqDTO, MyAccountQueryConditionBO.class);
        MyAccountListBO myAccountListBO = accountService.queryMyAccount(condition);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountListBO.getList(), MyAccountQueryResDTO.class);
        MyAccountListResDTO myAccountListResDTO = new MyAccountListResDTO();
        myAccountListResDTO.setPageNumber(reqDTO.getPageNumber());
        myAccountListResDTO.setPageSize(reqDTO.getPageSize());
        myAccountListResDTO.setTotalPages(myAccountListBO.getTotalPages());
        myAccountListResDTO.setList(myAccountQueryResDTOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResDTO);
    }

    /**
     * 不带条件的查询，返回带分页
     * @param decrypt 是否解密
     * @param pageNumber 页码
     * @param pageSize 条数
     * @return
     */
    @GetMapping("/list")
    public CommonResult<MyAccountListResDTO> list(@RequestParam(value = "decrypt", defaultValue = "false") Boolean decrypt,
                                                  @RequestParam(value = "page_number", defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        // 前端页面看到的第1页，分页参数是第0页
        if (pageNumber < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "请求页码不能小于1");
        }
        if (pageSize < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "每页数据不能小于1");
        }
        MyAccountListBO myAccountListBO = accountService.listMyAccount(decrypt, pageNumber, pageSize);
        List<MyAccountQueryResDTO> myAccountQueryResDTOList = BeanUtil.copyList(myAccountListBO.getList(), MyAccountQueryResDTO.class);
        MyAccountListResDTO myAccountListResDTO = new MyAccountListResDTO();
        myAccountListResDTO.setPageNumber(pageNumber);
        myAccountListResDTO.setPageSize(pageSize);
        myAccountListResDTO.setTotalPages(myAccountListBO.getTotalPages());
        myAccountListResDTO.setList(myAccountQueryResDTOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResDTO);
    }

    @PostMapping("/add")
    public CommonResult<MyAccountQueryResDTO> add(@RequestBody @Validated MyAccountAddReqDTO myAccountAddReqDTO) {
        MyAccountAddInfoBO myAccountAddBeforeBO = BeanUtil.copy(myAccountAddReqDTO, MyAccountAddInfoBO.class);
        MyAccountInfoBO myAccount = accountService.addMyAccount(myAccountAddBeforeBO);
        MyAccountQueryResDTO myAccountAddResDTO = BeanUtil.copy(myAccount, MyAccountQueryResDTO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
    }

    @PostMapping("/update")
    public CommonResult<MyAccountQueryResDTO> update(@RequestBody @Validated MyAccountUpdateReqDTO myAccountUpdateReqDTO) {
        MyAccountUpdateInfoBO myAccountUpdateBO = BeanUtil.copy(myAccountUpdateReqDTO, MyAccountUpdateInfoBO.class);
        MyAccountInfoBO myAccount = accountService.updateMyAccount(myAccountUpdateBO);
        MyAccountQueryResDTO myAccountUpdateResDTO = BeanUtil.copy(myAccount, MyAccountQueryResDTO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqDTO myAccountDeleteReqDTO) {
        accountService.deleteMyAccount(myAccountDeleteReqDTO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=account_data.xlsx");

        try {
            EasyExcel.write(response.getOutputStream(), MyAccountImportAndExportInfoBO.class)
                    .sheet("数据")
                    .doWrite(() -> accountService.exportAccounts());
        } catch (IOException e) {
            throw new CommonException(CommonCode.FAIL, "响应文件数据失败!");
        }
    }

    @PostMapping("/import")
    public CommonResult importAccounts(@RequestParam("file") MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), MyAccountImportAndExportInfoBO.class,
                    new PageReadListener<MyAccountImportAndExportInfoBO>(dataList -> accountService.importAccounts(dataList))
            ).sheet().doRead();
        } catch (Exception e) {
            log.error("导入数据失败！", e);
            return new CommonResult(CommonCode.FAIL, "导入数据失败！建议删除数据库文件重试。");
        }
        return new CommonResult(CommonCode.SUCCESS);
    }

}
