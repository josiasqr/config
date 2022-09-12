package com.bootcamp.credit.service;

import com.bootcamp.credit.client.WClient;
import com.bootcamp.credit.domain.credit.Credit;
import com.bootcamp.credit.domain.credit.CreditService;
import com.bootcamp.credit.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InCreditService implements CreditService {
    @Autowired
    private CreditRepository creditRepository;
    private WClient wClient;

    public InCreditService(CreditRepository creditRepository, WClient wClient) {
        this.creditRepository = creditRepository;
        this.wClient = wClient;
    }

    @Override
    public Flux<Credit> listCredits() {
        return this.creditRepository.findAll();
    }

    @Override
    public Mono<Credit> getCredit(String id) {
        return creditRepository.findById(id);
    }

    @Override
    public Flux<Credit> listCreditsCustomerDocument(String document) {
        return creditRepository.findByCustomerDocument(document);
    }

    @Override
    public Mono<Credit> createCredit(Credit credit) {
        return this.wClient.getCustomer(credit.getCustomerDocument())
                .flatMap(customer -> listCreditsCustomerDocument(customer.getDocument())
                        .count()
                        .flatMap(count -> {
                            if(credit.validate(customer.getType(), count)){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'Customer' Excedió el limite de 'Creditos'");
                            }
                            return this.creditRepository.save(credit);
                        })
                );
    }

    @Override
    public Mono<Credit> updateCredit(Credit credit) {
        return this.wClient.getCustomer(credit.getCustomerDocument())
                .flatMap(customer -> listCreditsCustomerDocument(customer.getDocument())
                        .count()
                        .flatMap(count -> {
                            if(credit.validate(customer.getType(), count)){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'Customer' Excedió el limite de 'Creditos'");
                            }
                            return this.creditRepository.save(credit);
                        })
                );
    }
}
