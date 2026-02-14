package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.domain.bo.AccountBO;
import drintau.accountmanager.webserver.domain.vo.AccountAddReqVO;
import drintau.accountmanager.webserver.domain.vo.AccountGetReqVO;
import drintau.accountmanager.webserver.domain.vo.AccountVO;
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


//    @PostMapping("/query")
//    public CommonResult<MyAccountListResVO> query(@RequestBody @Validated MyAccountQueryReqVO reqDTO) {
//        AccountFindConditionBO condition = BeanUtil.copy(reqDTO, AccountFindConditionBO.class);
//        AccountFindResultBO accountFindResultBO = accountService.queryMyAccount(condition);
//        List<MyAccountQueryResVO> myAccountQueryResVOList = BeanUtil.copyList(accountFindResultBO.getList(), MyAccountQueryResVO.class);
//        MyAccountListResVO myAccountListResVO = new MyAccountListResVO();
//        myAccountListResVO.setPageNumber(reqDTO.getPageNumber());
//        myAccountListResVO.setPageSize(reqDTO.getPageSize());
//        myAccountListResVO.setTotalPages(accountFindResultBO.getTotalPages());
//        myAccountListResVO.setTotalRecords(accountFindResultBO.getTotalRecords());
//        myAccountListResVO.setList(myAccountQueryResVOList);
//        return new CommonResult<>(CommonCode.SUCCESS, myAccountListResVO);
//    }
//    @PostMapping("/update")
//    public CommonResult<MyAccountQueryResVO> update(@RequestBody @Validated MyAccountUpdateReqVO myAccountUpdateReqVO) {
//        AccountBO myAccountUpdateBO = BeanUtil.copy(myAccountUpdateReqVO, AccountBO.class);
//        AccountBO myAccount = accountService.updateMyAccount(myAccountUpdateBO);
//        MyAccountQueryResVO myAccountUpdateResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
//        return new CommonResult<>(CommonCode.SUCCESS, myAccountUpdateResDTO);
//    }
//
//    @PostMapping("/delete")
//    public CommonResult delete(@RequestBody @Validated MyAccountDeleteReqVO myAccountDeleteReqVO) {
//        accountService.deleteMyAccount(myAccountDeleteReqVO.getId());
//        return new CommonResult(CommonCode.SUCCESS);
//    }
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
