package com.bootcamp.customer.domain;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<Customer> listCustomers();
    Mono<Customer> getCustomerDocument(String document);
    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> updateCustomer(Customer customer);
    Mono<Void> deleteCustomer(Customer customer);
}
