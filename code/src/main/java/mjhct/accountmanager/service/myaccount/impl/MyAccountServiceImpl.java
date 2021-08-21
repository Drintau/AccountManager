package mjhct.accountmanager.service.myaccount.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.dao.MyAccountRepository;
import mjhct.accountmanager.domain.bo.*;
import mjhct.accountmanager.domain.entity.MyAccountPO;
import mjhct.accountmanager.exception.BusinessException;
import mjhct.accountmanager.service.crypto.CryptoService;
import mjhct.accountmanager.service.myaccount.MyAccountService;
import mjhct.accountmanager.util.BeanUtil;
import mjhct.accountmanager.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "myAccountService")
public class MyAccountServiceImpl implements MyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountServiceImpl.class);

    @Resource(name = "aesService")
    private CryptoService cryptoService;

    @Resource
    private MyAccountRepository myAccountRepository;

    @Override
    public MyAccountInfoBO getMyAccountById(Integer id) {
        Optional<MyAccountPO> byId = myAccountRepository.findById(id);
        if (byId.isPresent()) {
            MyAccountPO myAccountPO = byId.get();
            MyAccountInfoBO myAccount = BeanUtil.copy(myAccountPO, MyAccountInfoBO.class);
            myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            return myAccount;
        }
        return null;
    }

    @Override
    public MyAccountListBO queryMyAccount(MyAccountQueryConditionBO condition) {
        // 按照条件的优先级进行查询，只要进行了某条件查询就返回；如没有条件匹配则走无条件的查询

        // 按照应用名称模糊查询
        if (StringUtils.hasText(condition.getFuzzyName())) {
            return listMyAccountByAppName(condition.getDecrypt(), condition.getFuzzyName(), condition.getOffsetPageNumber(), condition.getPageSize());
        }

        // 无条件分页查询
        return listMyAccount(condition.getDecrypt(), condition.getOffsetPageNumber(), condition.getPageSize());
    }

    private MyAccountListBO listMyAccountByAppName(Boolean decrypt, String appName, int offsetPageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(offsetPageNumber, pageSize);
        Page<MyAccountPO> pageData = myAccountRepository.findByAppNameContaining(appName, pageable);
        List<MyAccountPO> dataList = pageData.getContent();
        if (decrypt) {
            for (MyAccountPO myAccount : dataList) {
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            }
        }
        List<MyAccountInfoBO> myAccountInfoBOS = BeanUtil.copyList(dataList, MyAccountInfoBO.class);
        return new MyAccountListBO(pageData.getNumber(), pageData.getSize(), pageData.getTotalPages(), myAccountInfoBOS);
    }

    @Override
    public MyAccountListBO listMyAccount(Boolean decrypt, int offsetPageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(offsetPageNumber, pageSize);
        Page<MyAccountPO> pageData = myAccountRepository.findAll(pageable);
        List<MyAccountPO> dataList = pageData.getContent();
        if (decrypt) {
            for (MyAccountPO myAccount : dataList) {
                myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
                myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
            }
        }
        List<MyAccountInfoBO> myAccountInfoBOS = BeanUtil.copyList(dataList, MyAccountInfoBO.class);
        return new MyAccountListBO(pageData.getNumber(), pageData.getSize(), pageData.getTotalPages(), myAccountInfoBOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyAccountInfoBO addMyAccount(MyAccountAddInfoBO myAccountAddBO) {
        MyAccountPO addAccount = BeanUtil.copy(myAccountAddBO, MyAccountPO.class);
        addAccount.setMyUsername(cryptoService.encrypt(addAccount.getMyUsername()));
        addAccount.setMyPassword(cryptoService.encrypt(addAccount.getMyPassword()));
        OffsetDateTime nowOffsetDateTime = DateTimeUtil.nowChinaOffsetDateTime();
        addAccount.setCreateTime(nowOffsetDateTime);
        addAccount.setUpdateTime(nowOffsetDateTime);
        MyAccountPO save = myAccountRepository.save(addAccount);
        return BeanUtil.copy(save, MyAccountInfoBO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyAccountInfoBO updateMyAccount(MyAccountUpdateInfoBO myAccountUpdateBO) {
        Optional<MyAccountPO> old = myAccountRepository.findById(myAccountUpdateBO.getId());
        if (old.isPresent()) {
            MyAccountPO updateAccount = old.get();
            BeanUtil.copyProperties(myAccountUpdateBO, updateAccount, "id", "createTime", "updateTime");
            updateAccount.setMyUsername(cryptoService.encrypt(updateAccount.getMyUsername()));
            updateAccount.setMyPassword(cryptoService.encrypt(updateAccount.getMyPassword()));
            updateAccount.setUpdateTime(DateTimeUtil.nowChinaOffsetDateTime());
            MyAccountPO updateRst = myAccountRepository.save(updateAccount);
            return BeanUtil.copy(updateRst, MyAccountInfoBO.class);
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAccount(Integer id) {
        Optional<MyAccountPO> old = myAccountRepository.findById(id);
        if (old.isPresent()) {
            myAccountRepository.deleteById(id);
            return;
        }
        throw new BusinessException(CommonCode.FAIL, "未找到旧的账号");
    }

    @Override
    public ExcelWriter export() {
        // 查询所有数据
        List<MyAccountPO> rawDataList = myAccountRepository.findAll();
        List<MyAccountImportAndExportInfoBO> importAndExportDataList = BeanUtil.copyList(rawDataList, MyAccountImportAndExportInfoBO.class);
        // 解密
        for (MyAccountImportAndExportInfoBO myAccount : importAndExportDataList) {
            myAccount.setMyUsername(cryptoService.decrypt(myAccount.getMyUsername()));
            myAccount.setMyPassword(cryptoService.decrypt(myAccount.getMyPassword()));
        }

        // 写到xlsx
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("appName", "应用名称");
        writer.addHeaderAlias("appUrl", "应用网址");
        writer.addHeaderAlias("myUsername", "登录名");
        writer.addHeaderAlias("myPassword", "密码");
        writer.addHeaderAlias("remark", "说明");

        writer.write(importAndExportDataList);

        return writer;
    }

    @Override
    public void importAccounts(List<MyAccountImportAndExportInfoBO> importDataList) {
        for (MyAccountImportAndExportInfoBO importAccount : importDataList) {
            MyAccountPO insertPO = BeanUtil.copy(importAccount, MyAccountPO.class);
            insertPO.setMyUsername(cryptoService.encrypt(insertPO.getMyUsername()));
            insertPO.setMyPassword(cryptoService.encrypt(insertPO.getMyPassword()));
            OffsetDateTime nowOffsetDateTime = DateTimeUtil.nowChinaOffsetDateTime();
            insertPO.setCreateTime(nowOffsetDateTime);
            insertPO.setUpdateTime(nowOffsetDateTime);
            myAccountRepository.save(insertPO);
        }
    }

}
