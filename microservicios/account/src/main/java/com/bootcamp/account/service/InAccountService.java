package com.bootcamp.account.service;

import com.bootcamp.account.client.TAccount.TAccount;
import com.bootcamp.account.client.WClient;
import com.bootcamp.account.client.customer.Customer;
import com.bootcamp.account.domain.Account;
import com.bootcamp.account.domain.AccountService;
import com.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.bootcamp.account.util.Constants.CUSTOMER_TYPE_PERSONAL;
import static com.bootcamp.account.util.Constants.GET_CUSTOMER_DOCUMENT;

@Service
public class InAccountService implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    private WClient wClient;

    public InAccountService(AccountRepository accountRepository, WClient wClient) {
        this.accountRepository = accountRepository;
        this.wClient = wClient;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> listAccountsCustomerDocument(String document) {
        return accountRepository.findByCustomerDocument(document);
    }

    @Override
    public Account getNumberAccount(Long numberAccount) {
        return accountRepository.findByNumberAccount(numberAccount);
    }

    @Override
    public Account createAccount(Account account) {
        Customer customer = wClient.getCustomer(account.getCustomerDocument());

        if(customer.getType().equals(CUSTOMER_TYPE_PERSONAL) && (account.getTitular().size() > 1 || account.getSignatories().size() > 0)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer PERSONAL tiene 1 'Titualar' y 0 'Signatories'");
        }
        List<Account> acc = listAccountsCustomerDocument(account.getCustomerDocument())
                .stream()
                .filter(cus->cus.getType().equals(account.getType()))
                .collect(Collectors.toList());

        if(account.validate(customer.getType(), acc.size())){

            if(account.getBalance() > 0){
                Account newAccount = accountRepository.save(account);

                TAccount tAccount = TAccount.builder()
                        .numberAccount(newAccount.getNumberAccount())
                        .operation("APERTURA")
                        .amount(newAccount.getBalance())
                        .build();
                wClient.postTAccount(tAccount).subscribe();

                return newAccount;
            }

            return accountRepository.save(account);
        }

        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }
}
