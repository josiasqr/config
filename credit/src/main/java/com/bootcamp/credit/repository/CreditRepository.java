package com.bootcamp.credit.repository;

import com.bootcamp.credit.domain.credit.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {
    Flux<Credit> findByCustomerDocument(String document);
    //Flux<Credit> findByCustomerDocumentAndStatus(String document, String status);
}
