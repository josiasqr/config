package com.bootcamp.account.domain;

import java.util.List;

/**
 * Interface Service Account Entity.
 * */
public interface AccountService {
  List<Account> listAccounts();

  List<Account> listAccountsCustomerDocument(String document);

  Account getNumberAccount(Long numberAccount);

  Account createAccount(Account account);

  Account updateAccount(Account account);

  void deleteAccount(Account account);
}
