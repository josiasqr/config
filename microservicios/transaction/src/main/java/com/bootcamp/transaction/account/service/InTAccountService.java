package com.bootcamp.transaction.account.service;

import com.bootcamp.transaction.account.domain.TAccount;
import com.bootcamp.transaction.account.domain.TAccountService;
import com.bootcamp.transaction.account.repository.TAccountRepository;
import com.bootcamp.transaction.client.WClient;
import com.bootcamp.transaction.client.account.Account;
import com.bootcamp.transaction.credit.domain.TCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InTAccountService implements TAccountService {
    @Autowired
    KafkaTemplate<String, TAccount> kafkaTemplate;
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

        if(tAccount.getOperation().equals("APERTURA")){
            kafkaTemplate.send("movimientos", tAccount);

            return tAccountRepository.save(tAccount);
        }

        if(tAccount.getOperation().equals("DEPOSITO")){
            account.setBalance(account.getBalance()+tAccount.getAmount());
            wClient.putAccount(tAccount.getNumberAccount(), account);

            kafkaTemplate.send("movimientos", tAccount);

            return tAccountRepository.save(tAccount);
        }
        else if(tAccount.getOperation().equals("RETIRO")){
            if(tAccount.validate(account.getBalance(), tAccount.getAmount(), account.getCommission())){
                account.setBalance(account.getBalance()-tAccount.getAmount());
                wClient.putAccount(tAccount.getNumberAccount(), account);

                tAccount.setAmount(tAccount.getAmount()*-1);
                kafkaTemplate.send("movimientos", tAccount);

                return tAccountRepository.save(tAccount);
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente = S/" + account.getBalance());
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operación inválida = " + tAccount.getOperation());
    }
}
