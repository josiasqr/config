package com.bootcamp.customer.controller;

import com.bootcamp.customer.domain.Customer;
import com.bootcamp.customer.domain.CustomerService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Customers.
 * */
@RestController
@RequestMapping("/customers")
public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @GetMapping
  public Mono<ResponseEntity<Flux<Customer>>> listCustomers() {
    return Mono.just(ResponseEntity.ok(customerService.listCustomers()));
  }

  /**
   * Method Get javadoc.
   *
   * @GetMapping Customer for document.
   * */
  @GetMapping("/{document}")
  public Mono<ResponseEntity<Customer>> getCustomer(@PathVariable("document") String document) {
    return customerService.getCustomerDocument(document)
      .map(cus -> ResponseEntity.ok(cus))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Method Post Customer.
   * */
  @PostMapping
  public Mono<ResponseEntity<Customer>> createCustomer(@RequestBody Customer customer) {
    customer.setType(customer.getType().toUpperCase());
    customer.setRegistrationDate(LocalDateTime.now());
    return customerService.createCustomer(customer)
      .map(cus -> ResponseEntity.status(HttpStatus.CREATED).body(cus))
      .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  /**
   * Method Put Customer.
   * */
  @PutMapping("/{document}")
  public Mono<ResponseEntity<Customer>> updateAccount(@PathVariable("document") String document,
                                                      @RequestBody Customer customer) {
    return customerService.getCustomerDocument(document)
      .flatMap(cus -> {
        cus.setName(customer.getName());
        cus.setDocument(customer.getDocument());
        cus.setType(customer.getType().toUpperCase());
        cus.setRegistrationDate(LocalDateTime.now());

        return customerService.updateCustomer(cus);
      }).map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res))
      .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * Method Delete Customer.
   * */
  @DeleteMapping("/{document}")
  public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable("document") String document,
                                                  Customer customer) {
    return customerService.getCustomerDocument(document)
      .flatMap(acc -> customerService.deleteCustomer(acc)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
