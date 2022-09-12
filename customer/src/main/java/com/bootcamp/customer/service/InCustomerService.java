package com.bootcamp.customer.service;

import com.bootcamp.customer.domain.Customer;
import com.bootcamp.customer.domain.CustomerService;
import com.bootcamp.customer.repository.CustomerRepository;
import com.bootcamp.customer.service.kafka.CustomerEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InCustomerService implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private final CustomerEventsService customerEventsService;

    public InCustomerService(CustomerRepository customerRepository, CustomerEventsService customerEventsService) {
        this.customerRepository = customerRepository;
        this.customerEventsService = customerEventsService;
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
        Mono<Customer> cus = customerRepository.save(customer);
        //cus.subscribe(c -> this.customerEventsService.publish(c));

        return cus;
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
