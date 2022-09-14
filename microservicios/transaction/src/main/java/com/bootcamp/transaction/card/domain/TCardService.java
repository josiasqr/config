package com.bootcamp.transaction.card.domain;

import java.util.List;

public interface TCardService {
  List<TCard> listTransactions();

  List<TCard> listTransactionNumberAccount(Long number);

  TCard getCodeTransaction(String code);

  TCard createTransaction(TCard tCard);
}
