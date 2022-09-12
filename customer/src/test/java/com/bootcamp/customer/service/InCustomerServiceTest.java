package com.bootcamp.customer.service;

import com.bootcamp.customer.domain.Customer;
import com.bootcamp.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class InCustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    InCustomerService inCustomerService;

    @Autowired
    private Mono<Customer> customer;

    @Autowired
    private Flux<Customer> listCustomers;

    @BeforeEach
    void ini(){
        Customer customer1 = Customer.builder()
                .id("631cafab26089a6f671f213e")
                .name("Juan Perez")
                .document("23498061")
                .type("PERSONAL")
                .registrationDate(LocalDateTime.parse("2022-09-10T10:39:23"))
                .build();
        Customer customer2 = Customer.builder()
                .id("631cafbc26089a6f671f213f")
                .name("Linux")
                .document("20052376903")
                .type("EMPRESARIAL")
                .registrationDate(LocalDateTime.parse("2022-09-10T10:39:40.503"))
                .build();
        Customer customer3 = Customer.builder()
                .id("631cafe526089a6f671f2140")
                .name("Josias Quiñonez")
                .document("75849750")
                .type("PERSONAL")
                .registrationDate(LocalDateTime.parse("2022-09-10T10:40:21.967"))
                .build();
        customer = Mono.just(customer1);

        listCustomers = Flux.just(customer1, customer2, customer3);
    }

    @Test
    void findByDocuments() {
        Mockito.when(customerRepository.findByDocument("23498061")).thenReturn(customer);
        Mono<Customer> obj = inCustomerService.getCustomerDocument("23498061");
        assertEquals(customer, obj);
        customer.subscribe(x -> assertEquals("Juan Perez", x.getName()));
        customer.subscribe(x -> assertEquals("23498061", x.getDocument()));
        customer.subscribe(x -> assertEquals("PERSONAL", x.getType()));
    }

    @Test
    void listCustomers(){
        Mockito.when(customerRepository.findAll()).thenReturn(listCustomers);
        Flux<Customer> obj = inCustomerService.listCustomers();
        assertEquals(listCustomers, obj);
    }
}
