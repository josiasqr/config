package com.bootcamp.product.credit.controller;

import com.bootcamp.product.credit.domain.Credit;
import com.bootcamp.product.credit.domain.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/credits")
public class CreditController {
  @Autowired
  private CreditService creditService;

  public CreditController(CreditService creditService) {
    this.creditService = creditService;
  }

  @GetMapping
  public Mono<ResponseEntity<Flux<Credit>>> listCredits() {
    return Mono.just(ResponseEntity.ok(creditService.listCredits()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Credit>> getCredit(@PathVariable("id") String id) {
    return creditService.getCredit(id)
      .map(credit -> ResponseEntity.ok(credit))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/document/{document}")
  public Mono<ResponseEntity<Flux<Credit>>> getCreditsCustomerDocument(@PathVariable String document) {
    return Mono.just(ResponseEntity.ok().body(creditService.listCreditsCustomerDocument(document)));
  }

  @PostMapping
  public Mono<ResponseEntity<Credit>> createCredit(@Valid @RequestBody Mono<Credit> creditMono) {
    Mono<Credit> monoTest = creditMono.map(credit -> {
      credit.setStatus("CREDITO");
      credit.setRegistrationDate(LocalDateTime.now());
      return credit;
    });
    return monoTest.flatMap(credit -> creditService.createCredit(credit)
      .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
    );
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Credit>> updateCredit(@PathVariable("id") String id, @RequestBody Credit credit) {
    credit.setId(id);
    return creditService.getCredit(id)
      .flatMap(cr -> {
        cr.setAmount(credit.getAmount());
        cr.setStatus(credit.getStatus());
        cr.setExpirationDate(credit.getExpirationDate());

        return creditService.updateCredit(cr);
      }).map(cr -> ResponseEntity.status(HttpStatus.CREATED).body(cr))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
