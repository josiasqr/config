package com.bootcamp.transaction.card.service;

import com.bootcamp.transaction.card.domain.TCard;
import com.bootcamp.transaction.card.domain.TCardService;
import com.bootcamp.transaction.card.repository.TCardRepository;
import com.bootcamp.transaction.client.WClient;
import com.bootcamp.transaction.client.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InTCardService implements TCardService {
    @Autowired
    private TCardRepository tCardRepository;

    private WClient wClient;

    public InTCardService(TCardRepository tCardRepository, WClient wClient) {
        this.tCardRepository = tCardRepository;
        this.wClient = wClient;
    }

    @Override
    public List<TCard> listTransactions() {
        return tCardRepository.findAll();
    }

    @Override
    public List<TCard> listTransactionNumberAccount(Long number) {
        return tCardRepository.findByNumberAccount(number);
    }

    @Override
    public TCard getCodeTransaction(String code) {
        return tCardRepository.findByCode(code);
    }

    @Override
    public TCard createTransaction(TCard tCard) {
        if(tCard.getOperation().equals("CREDITO")){
            return tCardRepository.save(tCard);
        }
        else if(tCard.getOperation().equals("CONSUMO")){
            Card card = wClient.getCard(tCard.getNumberAccount());
            if(tCard.validate(card.getLimitCredit(), tCard.getAmount())){
                card.setLimitCredit(card.getLimitCredit()-tCard.getAmount());
                wClient.putCard(card.getNumberAccount(), card);

                tCard.setAmount(tCard.getAmount()*-1);
                return tCardRepository.save(tCard);
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente = S/" + card.getLimitCredit());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operación inválida = " + tCard.getOperation());
    }
}
