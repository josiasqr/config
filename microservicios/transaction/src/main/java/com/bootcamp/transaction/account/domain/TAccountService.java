package com.bootcamp.transaction.account.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface TAccountService {
    List<TAccount> listTransactions();
    List<TAccount> listTransactionsOfWeek(Long number, LocalDateTime dateStart, LocalDateTime dateEnd);
    List<TAccount> listTransactionNumberAccount(Long number);
    TAccount getCodeTransaction(String code);
    TAccount createTransaction(TAccount tAccount);
}
