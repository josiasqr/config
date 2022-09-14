package com.bootcamp.customer.service;

import com.bootcamp.customer.domain.Customer;
import com.bootcamp.customer.domain.CustomerService;
import com.bootcamp.customer.repository.CustomerRepository;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InCustomerService implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public InCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> getCustomerDocument(String document) {

        return customerRepository.findByDocument(document);
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {

        try {
            Mono<Customer> customerMono = customerRepository.save(customer);
            return customerMono;
        }catch (MongoWriteConcernException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ya existe un usuario con el document = '" + customer.getDocument());
        }
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Void> deleteCustomer(Customer customer) {
        return customerRepository.delete(customer);
    }
}
