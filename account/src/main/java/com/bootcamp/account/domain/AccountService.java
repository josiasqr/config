package com.bootcamp.account.domain;

import com.bootcamp.account.client.customer.Customer;

import java.util.List;

public interface AccountService {
    List<Account> listAccounts();
    List<Account> listAccountsCustomerDocument(String document);
    Account getNumberAccount(Long numberAccount);
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(Account account);
}
