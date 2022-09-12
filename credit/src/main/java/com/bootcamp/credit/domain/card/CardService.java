package com.bootcamp.credit.domain.card;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardService {
    Flux<Card> listCards();
    Mono<Card> getNumberAccount(Long number);
    Flux<Card> listCardsCustomerDocument(String document);
    Mono<Card> createCard(Card card);
    Mono<Card> updateCard(Card card);
}
