package com.bootcamp.product.credit.repository;

import com.bootcamp.product.credit.domain.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {
  Flux<Credit> findByCustomerDocument(String document);
  //Flux<Credit> findByCustomerDocumentAndStatus(String document, String status);
}
