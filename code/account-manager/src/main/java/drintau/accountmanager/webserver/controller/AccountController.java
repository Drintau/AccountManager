package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindConditionBO;
import drintau.accountmanager.webserver.domain.bo.AccountFindResultBO;
import drintau.accountmanager.webserver.domain.vo.*;
import drintau.accountmanager.webserver.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/query")
//    public CommonResult<MyAccountListResVO> query(@RequestBody @Validated MyAccountQueryReqVO reqDTO) {
//        AccountFindConditionBO condition = BeanUtil.copy(reqDTO, AccountFindConditionBO.class);
//        AccountFindResultBO accountFindResultBO = accountService.queryMyAccount(condition);
//        List<MyAccountQueryResVO> myAccountQueryResVOList = BeanUtil.copyList(accountFindResultBO.getList(), MyAccountQueryResVO.class);
//        MyAccountListResVO myAccountListResVO = new MyAccountListResVO();
//        myAccountListResVO.setpageNum(reqDTO.getpageNum());
//        myAccountListResVO.setPageSize(reqDTO.getPageSize());
//        myAccountListResVO.setTotalPages(accountFindResultBO.getTotalPages());
//        myAccountListResVO.setTotalRecords(accountFindResultBO.getTotalRecords());
//        myAccountListResVO.setList(myAccountQueryResVOList);
//        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResVO);
//    }
//
//
//    @GetMapping("/export")
//    public void export(HttpServletResponse response) {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        response.setHeader("Content-Disposition","attachment;filename=exportdata.xlsx");
//
//        try {
//            EasyExcel.write(response.getOutputStream(), AccountIEBO.class)
//                    .sheet("数据")
//                    .doWrite(() -> accountService.exportMyAccounts());
//        } catch (IOException e) {
//            throw new CommonException(CommonCode.FAIL, "响应文件数据失败!");
//        }
//    }
//
//    @PostMapping("/import")
//    public CommonResult importAccounts(@RequestParam("file") MultipartFile file) {
//        try {
//            EasyExcel.read(file.getInputStream(), AccountIEBO.class,
//                    new PageReadListener<AccountIEBO>(dataList -> accountService.importMyAccounts(dataList))
//            ).sheet().doRead();
//        } catch (Exception e) {
//            log.error("导入数据失败！", e);
//            return new CommonResult(CommonCode.FAIL, "导入数据失败！建议删除数据库文件重试。");
//        }
//        return new CommonResult(CommonCode.SUCCESS);
//    }

}
