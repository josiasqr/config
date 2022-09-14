package com.bootcamp.transaction.credit.domain;


import java.util.List;

public interface TCreditService {
  List<TCredit> listTransactions();

  List<TCredit> listTransactionIdCredits(String idCredit);

  TCredit getCodeTransaction(String code);

  TCredit createTransaction(TCredit tCredit);
}
