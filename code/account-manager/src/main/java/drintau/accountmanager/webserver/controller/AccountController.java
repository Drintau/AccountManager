package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.webserver.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

//    @GetMapping("/get")
//    public CommonResult<MyAccountQueryResVO> get(@RequestParam(value = "id") Integer id) {
//        if (!NumberUtil.isNotNullAndGreaterThanZero(id)) {
//            throw new BusinessException(CommonCode.REQUEST_PARAMETER_ERROR, "查询id非法！");
//        }
//        AccountBO myAccountById = accountService.getMyAccountById(id);
//        if (myAccountById == null) {
//            throw new BusinessException(CommonCode.FAIL, "查询数据不存在，请刷新页面！");
//        }
//        MyAccountQueryResVO myAccountQueryResVO = BeanUtil.copy(myAccountById, MyAccountQueryResVO.class);
//        return new CommonResult<>(CommonCode.SUCCESS, myAccountQueryResVO);
//    }
//
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
//
//    @PostMapping("/add")
//    public CommonResult<MyAccountQueryResVO> add(@RequestBody @Validated MyAccountAddReqVO myAccountAddReqVO) {
//        AccountBO myAccountAddBeforeBO = BeanUtil.copy(myAccountAddReqVO, AccountBO.class);
//        AccountBO myAccount = accountService.addMyAccount(myAccountAddBeforeBO);
//        MyAccountQueryResVO myAccountAddResDTO = BeanUtil.copy(myAccount, MyAccountQueryResVO.class);
//        return new CommonResult<>(CommonCode.SUCCESS, myAccountAddResDTO);
//    }
//
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
