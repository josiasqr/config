package com.bootcamp.transaction.account.domain;

import java.util.List;

public interface TAccountService {
    List<TAccount> listTransactions();
    List<TAccount> listTransactionNumberAccount(Long number);
    TAccount getCodeTransaction(String code);
    TAccount createTransaction(TAccount tAccount);
}
