package com.bootcamp.transaction.credit.service;

import com.bootcamp.transaction.client.WClient;
import com.bootcamp.transaction.client.credit.Credit;
import com.bootcamp.transaction.credit.domain.TCredit;
import com.bootcamp.transaction.credit.domain.TCreditService;
import com.bootcamp.transaction.credit.repository.TCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InTCreditService implements TCreditService {
    @Autowired
    private TCreditRepository tCreditRepository;

    private WClient wClient;

    public InTCreditService(TCreditRepository tCreditRepository, WClient wClient) {
        this.tCreditRepository = tCreditRepository;
        this.wClient = wClient;
    }

    @Override
    public List<TCredit> listTransactions() {
        return tCreditRepository.findAll();
    }

    @Override
    public List<TCredit> listTransactionIdCredits(String idCredit) {
        return tCreditRepository.findByIdCredit(idCredit);
    }

    @Override
    public TCredit getCodeTransaction(String code) {
        return tCreditRepository.findByCode(code);
    }

    @Override
    public TCredit createTransaction(TCredit tCredit) {
        if(tCredit.getOperation().equals("CREDITO")){
            return tCreditRepository.save(tCredit);
        }
        else if(tCredit.getOperation().equals("PAGO")){
            Credit credit = wClient.getCredit(tCredit.getIdCredit());

            if(tCredit.validate(credit.getAmount(), tCredit.getAmount())){
                credit.setAmount(credit.getAmount()-tCredit.getAmount());

                wClient.putCredit(tCredit.getIdCredit(), credit);

                return tCreditRepository.save(tCredit);
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pago inválido, deuda pendiente = S/" + credit.getAmount());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operación inválida = " + tCredit.getOperation());
    }
}
