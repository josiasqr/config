package com.bootcamp.credit.service;

import com.bootcamp.credit.client.WClient;
import com.bootcamp.credit.domain.card.Card;
import com.bootcamp.credit.domain.card.CardService;
import com.bootcamp.credit.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InCardService implements CardService {
    @Autowired
    private CardRepository cardRepository;
    private WClient wClient;

    public InCardService(CardRepository cardRepository, WClient wClient) {
        this.cardRepository = cardRepository;
        this.wClient = wClient;
    }

    @Override
    public Flux<Card> listCards() {
        return cardRepository.findAll();
    }

    @Override
    public Mono<Card> getNumberAccount(Long number) {
        return cardRepository.findByNumberAccount(number);
    }

    @Override
    public Flux<Card> listCardsCustomerDocument(String document) {
        return cardRepository.findByCustomerDocument(document);
    }

    @Override
    public Mono<Card> createCard(Card card) {
        return wClient.getCustomer(card.getCustomerDocument())
                .flatMap(customer -> cardRepository.save(card));
    }

    @Override
    public Mono<Card> updateCard(Card card) {
        return wClient.getCustomer(card.getCustomerDocument())
                .flatMap(customer -> cardRepository.save(card));
    }
}
