package drintau.accountmanager.webserver.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.exception.CommonException;
import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.commons.util.NumberUtil;
import drintau.accountmanager.webserver.domain.bo.MyAccountBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountImportAndExportBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountListBO;
import drintau.accountmanager.webserver.domain.bo.MyAccountQueryConditionBO;
import drintau.accountmanager.webserver.domain.vo.*;
import drintau.accountmanager.webserver.service.MyAccountService;
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
public class MyAccountController {

    @Resource(name = "myAccountService")
    private MyAccountService myAccountService;

    /**
     * id 精确查询，且解密
     * @return
     */
    @GetMapping("/get")
    public CommonResult<MyAccountQueryResVO> get(@RequestParam(value = "id") Integer id) {
        if (!NumberUtil.isNotNullAndGreaterThanZero(id)) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "查询id非法！");
        }
        MyAccountBO myAccountById = myAccountService.getMyAccountById(id);
        if (myAccountById == null) {
            throw new BusinessException(CommonCode.FAIL, "查询数据不存在，请刷新页面！");
        }
        MyAccountQueryResVO myAccountQueryResVO = BeanUtil.copy(myAccountById, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResVO);
    }

    /**
     * 条件查询，返回带分页
     * @param reqDTO
     * @return
     */
    @PostMapping("/query")
    public CommonResult<MyAccountListResVO> query(@RequestBody @Validated MyAccountQueryReqVO reqDTO) {
        MyAccountQueryConditionBO condition = BeanUtil.copy(reqDTO, MyAccountQueryConditionBO.class);
        MyAccountListBO myAccountListBO = myAccountService.queryMyAccount(condition);
        List<MyAccountQueryResVO> myAccountQueryResVOList = BeanUtil.copyList(myAccountListBO.getList(), MyAccountQueryResVO.class);
        MyAccountListResVO myAccountListResVO = new MyAccountListResVO();
        myAccountListResVO.setPageNumber(reqDTO.getPageNumber());
        myAccountListResVO.setPageSize(reqDTO.getPageSize());
        myAccountListResVO.setTotalPages(myAccountListBO.getTotalPages());
        myAccountListResVO.setList(myAccountQueryResVOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResVO);
    }

    /**
     * 不带条件的查询，返回带分页
     * @param decrypt 是否解密
     * @param pageNumber 页码
     * @param pageSize 条数
     * @return
     */
    @GetMapping("/list")
    public CommonResult<MyAccountListResVO> list(@RequestParam(value = "decrypt", defaultValue = "false") Boolean decrypt,
                                                 @RequestParam(value = "page_number", defaultValue = "1") Integer pageNumber,
                                                 @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        // 前端页面看到的第1页，分页参数是第0页
        if (pageNumber < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "请求页码不能小于1");
        }
        if (pageSize < 1) {
            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "每页数据不能小于1");
        }
        MyAccountListBO myAccountListBO = myAccountService.listMyAccount(decrypt, pageNumber, pageSize);
        List<MyAccountQueryResVO> myAccountQueryResVOList = BeanUtil.copyList(myAccountListBO.getList(), MyAccountQueryResVO.class);
        MyAccountListResVO myAccountListResVO = new MyAccountListResVO();
        myAccountListResVO.setPageNumber(pageNumber);
        myAccountListResVO.setPageSize(pageSize);
        myAccountListResVO.setTotalPages(myAccountListBO.getTotalPages());
        myAccountListResVO.setList(myAccountQueryResVOList);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResVO);
    }

    @PostMapping("/add")
    public CommonResult<MyAccountQueryResVO> add(@RequestBody @Validated MyAccountAddReqVO myAccountAddReqVO) {
        MyAccountBO myAccountAddBeforeBO = BeanUtil.copy(myAccountAddReqVO, MyAccountBO.class);
        MyAccountBO myAccount = myAccountService.addMyAccount(myAccountAddBeforeBO);
        MyAccountQueryResVO myAccountAddResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
    }

    @PostMapping("/update")
    public CommonResult<MyAccountQueryResVO> update(@RequestBody @Validated MyAccountUpdateReqVO myAccountUpdateReqVO) {
        MyAccountBO myAccountUpdateBO = BeanUtil.copy(myAccountUpdateReqVO, MyAccountBO.class);
        MyAccountBO myAccount = myAccountService.updateMyAccount(myAccountUpdateBO);
        MyAccountQueryResVO myAccountUpdateResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqVO myAccountDeleteReqVO) {
        myAccountService.deleteMyAccount(myAccountDeleteReqVO.getId());
        return new CommonResult(CommonCode.SUCCESS);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=account_data.xlsx");

        try {
            EasyExcel.write(response.getOutputStream(), MyAccountImportAndExportBO.class)
                    .sheet("数据")
                    .doWrite(() -> myAccountService.exportMyAccounts());
        } catch (IOException e) {
            throw new CommonException(CommonCode.FAIL, "响应文件数据失败!");
        }
    }

    @PostMapping("/import")
    public CommonResult importAccounts(@RequestParam("file") MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), MyAccountImportAndExportBO.class,
                    new PageReadListener<MyAccountImportAndExportBO>(dataList -> myAccountService.importMyAccounts(dataList))
            ).sheet().doRead();
        } catch (Exception e) {
            log.error("导入数据失败！", e);
            return new CommonResult(CommonCode.FAIL, "导入数据失败！建议删除数据库文件重试。");
        }
        return new CommonResult(CommonCode.SUCCESS);
    }

}
