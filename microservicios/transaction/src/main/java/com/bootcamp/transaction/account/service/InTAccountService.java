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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<TAccount> listTransactionsOfWeek(Long number, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return tAccountRepository.findAllByNumberAccountAndRegistrationDateBetween(number, dateStart, dateEnd);
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

        // commission
        List<TAccount> countTransactions = listTransactionsOfWeek(tAccount.getNumberAccount(), tAccount.firstDayWeek(), LocalDateTime.now())
                .stream()
                .filter(tAcc -> tAcc.getOperation().equals("DEPOSITO") || tAcc.getOperation().equals("RETIRO"))
                .collect(Collectors.toList());
        TAccount commission = TAccount.builder()
                .code(tAccount.code())
                .numberAccount(tAccount.getNumberAccount())
                .operation("COMISION")
                .amount(-7.5)
                .registrationDate(LocalDateTime.now())
                .build();
        System.out.println("countTransactions.size() = " + countTransactions.size());

        switch (tAccount.getOperation()){
            case "APERTURA":
                TAccount newTAccount = tAccountRepository.save(tAccount);

                kafkaTemplate.send("movimientos", newTAccount); // Kafka producer

                return newTAccount;

            case "DEPOSITO":
                account.setBalance(account.getBalance()+tAccount.getAmount());
                System.out.println("account.getBalance() = " + account.getBalance());
                wClient.putAccount(tAccount.getNumberAccount(), account);

                TAccount newTAccount1 = tAccountRepository.save(tAccount);

                kafkaTemplate.send("movimientos", newTAccount1); // Kafka producer

                if(tAccount.commission(account.getLimitTransaction(), countTransactions.size()+1)){
                    account.setBalance(account.getBalance()-7.5);
                    System.out.println("account.getBalance() comision = " + account.getBalance());
                    wClient.putAccount(tAccount.getNumberAccount(), account);
                    tAccountRepository.save(commission);
                }

                return newTAccount1;

            case "RETIRO":
                if(tAccount.validate(account.getBalance(), tAccount.getAmount(), 7.5)){
                    account.setBalance(account.getBalance()-tAccount.getAmount());
                    wClient.putAccount(tAccount.getNumberAccount(), account);

                    tAccount.setAmount(tAccount.getAmount()*-1);

                    TAccount newTAccount2 = tAccountRepository.save(tAccount);

                    kafkaTemplate.send("movimientos", newTAccount2); // Kafka producer

                    if(tAccount.commission(account.getLimitTransaction(), countTransactions.size()+1)){
                        account.setBalance(account.getBalance()-7.5);
                        wClient.putAccount(tAccount.getNumberAccount(), account);
                        tAccountRepository.save(commission);
                    }

                    return newTAccount2;
                }

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente = S/" + account.getBalance());

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operación inválida = " + tAccount.getOperation());
        }
    }
}
