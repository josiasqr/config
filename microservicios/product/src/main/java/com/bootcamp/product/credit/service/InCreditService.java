package com.bootcamp.product.credit.service;

import com.bootcamp.product.client.WClient;
import com.bootcamp.product.client.tcredit.TCredit;
import com.bootcamp.product.credit.domain.Credit;
import com.bootcamp.product.credit.domain.CreditService;
import com.bootcamp.product.credit.repository.CreditRepository;
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
        return wClient.getCustomer(credit.getCustomerDocument())
                .flatMap(customer -> listCreditsCustomerDocument(customer.getDocument())
                        .count()
                        .flatMap(count -> {
                            if(credit.validate(customer.getType(), count)){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer no puede registrar mas Créditos");
                            }

                            return creditRepository.save(credit).map(cr->{
                                TCredit tCredit = TCredit.builder()
                                        .idCredit(cr.getId())
                                        .operation("CREDITO")
                                        .amount(cr.getAmount())
                                        .build();
                                wClient.postTCredit(tCredit).subscribe();

                                return cr;
                            });
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
