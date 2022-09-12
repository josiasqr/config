package com.bootcamp.transaction.account.service;

import com.bootcamp.transaction.account.domain.TAccount;
import com.bootcamp.transaction.account.domain.TAccountService;
import com.bootcamp.transaction.account.repository.TAccountRepository;
import com.bootcamp.transaction.client.WClient;
import com.bootcamp.transaction.client.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InTAccountService implements TAccountService {
    @Autowired
    private TAccountRepository tAccountRepository;

    private WClient wClient;

    public InTAccountService(TAccountRepository tAccountRepository, WClient wClient) {
        this.tAccountRepository = tAccountRepository;
        this.wClient = wClient;
    }

    @Override
    public List<TAccount> listTransactions() {
        return tAccountRepository.findAll();
    }

    @Override
    public TAccount getCodeTransaction(String code) {
        return tAccountRepository.findByCode(code);
    }

    @Override
    public List<TAccount> listTransactionNumberAccount(Long number) {
        return tAccountRepository.findByNumberAccount(number);
    }

    @Override
    public TAccount createTransaction(TAccount tAccount) {
        Account account = wClient.getAccount(tAccount.getNumberAccount());

        System.out.println("Account of microservice:" + account);

        if(tAccount.getOperation().equals("DEPOSITO")){
            account.setBalance(account.getBalance()+tAccount.getAmount());
            wClient.putAccount(tAccount.getNumberAccount(), account);

            return tAccountRepository.save(tAccount);
        }
        if(tAccount.validate(account.getBalance(), tAccount.getAmount(), account.getCommission())){
            account.setBalance(account.getBalance()-tAccount.getAmount());
            wClient.putAccount(tAccount.getNumberAccount(), account);

            tAccount.setAmount(tAccount.getAmount()*-1);
            return tAccountRepository.save(tAccount);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No cuenta con Saldo Suficiente'");
    }
}
