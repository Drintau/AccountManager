package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.AccountTransferBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AccountTransferImportListener extends AnalysisEventListener<AccountTransferBO> {

    private final List<AccountTransferBO> accountTransferBOList = new ArrayList<>();

    private final AccountService accountService;

    public AccountTransferImportListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void invoke(AccountTransferBO accountTransferBO, AnalysisContext analysisContext) {
        accountTransferBOList.add(accountTransferBO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        accountService.transferImport(accountTransferBOList);
    }
}
