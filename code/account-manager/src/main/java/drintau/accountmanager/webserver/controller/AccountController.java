package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindConditionBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindResultBO;
import drintau.accountmanager.webserver.domain.bo.AccountTransferBO;
import drintau.accountmanager.webserver.domain.vo.*;
import drintau.accountmanager.webserver.service.AccountService;
import drintau.accountmanager.webserver.service.AccountTransferImportListener;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.fesod.sheet.FesodSheet;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/get")
    public CommonResult<AccountVO> get(@RequestBody @Validated AccountGetReqVO reqVO) {
        AccountBO bo = accountService.getAccount(reqVO.getId());
        return new CommonResult<>(BeanUtil.copy(bo, AccountVO.class));
    }

    @PostMapping("/add")
    public CommonResult<AccountVO> add(@RequestBody @Validated AccountAddReqVO reqVO) {
        AccountBO bo = BeanUtil.copy(reqVO, AccountBO.class);
        bo = accountService.addAccount(bo);
        return new CommonResult<>(BeanUtil.copy(bo, AccountVO.class));
    }

    @PostMapping("/update")
    public CommonResult<AccountVO> update(@RequestBody @Validated AccountUpdateReqVO reqVO) {
        AccountBO bo = BeanUtil.copy(reqVO, AccountBO.class);
        bo = accountService.updateAccount(bo);
        return new CommonResult<>(BeanUtil.copy(bo, AccountVO.class));
    }

    @PostMapping("/delete")
    public CommonResult<Void> delete(@RequestBody @Validated AccountDeleteReqVO reqVO) {
        accountService.deleteAccount(reqVO.getId());
        return new CommonResult<>();
    }

    @PostMapping("/find")
    public CommonResult<AccountFindResVO> find(@RequestBody @Validated AccountFindReqVO reqVO) {
        AccountFindResultBO resultBO = accountService.findAccount(BeanUtil.copy(reqVO, AccountFindConditionBO.class));
        AccountFindResVO resVO = BeanUtil.copy(resultBO, AccountFindResVO.class);
        resVO.setList(BeanUtil.copyList(resultBO.getList(), AccountVO.class));
        return new CommonResult<>(resVO);
    }

    @PostMapping("/import")
    public CommonResult<Void> transferImport(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("请上传文件");
        }

        try {
            FesodSheet.read(file.getInputStream(), AccountTransferBO.class, new AccountTransferImportListener(accountService)).sheet().doRead();
        } catch (IOException e) {
            log.error("文件处理失败", e);
            throw new BusinessException("文件处理失败");
        }
        return new CommonResult<>();
    }

    @GetMapping("/export")
    public void transferExport(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            List<AccountTransferBO> accountTransferExportBOList = accountService.transferExport();

            String rawFileName = CollectionUtils.isEmpty(accountTransferExportBOList) ? "导入模板" : "导出数据";
            String fileName = URLEncoder.encode(rawFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            FesodSheet.write(response.getOutputStream(), AccountTransferBO.class)
                    .sheet("账号")
                    .doWrite(accountTransferExportBOList);

        } catch (Exception e) {
            log.error("文件生成失败", e);
            throw new BusinessException("文件生成失败");
        }
    }

}
