package com.bootcamp.credit.repository;

import com.bootcamp.credit.domain.card.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CardRepository extends ReactiveMongoRepository<Card, String> {
    Flux<Card> findByCustomerDocument(String document);
    Mono<Card> findByNumberAccount(Long number);
}
