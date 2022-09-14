package com.bootcamp.customer.repository;

import com.bootcamp.customer.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, Integer> {
  Flux<Customer> findByType(String type);
  Mono<Customer> findByDocument(String document);
  Flux<Customer> findAllByRegistrationDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);
}
