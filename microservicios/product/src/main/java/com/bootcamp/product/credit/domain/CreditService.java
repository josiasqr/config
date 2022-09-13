package com.bootcamp.product.credit.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    Flux<Credit> listCredits();
    Mono<Credit> getCredit(String id);
    Flux<Credit> listCreditsCustomerDocument(String document);
    Mono<Credit> createCredit(Credit credit);
    Mono<Credit> updateCredit(Credit credit);
}
