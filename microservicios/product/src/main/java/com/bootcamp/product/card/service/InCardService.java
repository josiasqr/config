package com.bootcamp.product.card.service;

import com.bootcamp.product.client.tcard.TCard;
import com.bootcamp.product.client.WClient;
import com.bootcamp.product.card.domain.Card;
import com.bootcamp.product.card.domain.CardService;
import com.bootcamp.product.card.repository.CardRepository;
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
        Mono<Card> cardMono = wClient.getCustomer(card.getCustomerDocument())
                .flatMap(customer -> cardRepository.save(card));

        // registrar transaccion inicial Card
        TCard tCard = TCard.builder()
                .numberAccount(card.getNumberAccount())
                .operation("CREDITO")
                .amount(card.getLimitCredit())
                .build();
        Mono<TCard> tCard1 = wClient.postTCard(tCard);
        tCard1.subscribe(t-> System.out.println("New tCard post = " + t));
        
        return cardMono;
    }

    @Override
    public Mono<Card> updateCard(Card card) {
        return wClient.getCustomer(card.getCustomerDocument())
                .flatMap(customer -> cardRepository.save(card));
    }
}
