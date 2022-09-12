package com.bootcamp.credit.controller;

import com.bootcamp.credit.domain.card.CardService;
import com.bootcamp.credit.domain.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Card>>> listCredits(){
        return Mono.just(ResponseEntity.ok(cardService.listCards()));
    }

    @GetMapping("/{numberAccount}")
    public Mono<ResponseEntity<Card>> getCredit(@PathVariable("numberAccount") Long numberAccount){
        return cardService.getNumberAccount(numberAccount)
                .map(card -> ResponseEntity.ok(card))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/document/{document}")
    public Mono<ResponseEntity<Flux<Card>>> getCreditsCustomerDocument(@PathVariable String document){
        return Mono.just(ResponseEntity.ok().body(cardService.listCardsCustomerDocument(document)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Card>> createCredit(@Valid @RequestBody Mono<Card> cardMono){
        Mono<Card> monoTest = cardMono.map(card -> {
            card.setRegistrationDate(LocalDateTime.now());
            return card;
        });
        return monoTest.flatMap(card -> cardService.createCard(card)
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
        );
    }

    @PutMapping("/{numberAccount}")
    public Mono<ResponseEntity<Card>> updateCard(@PathVariable("numberAccount") Long numberAccount, @RequestBody Card card){
        card.setId(card.getId());
        return cardService.getNumberAccount(numberAccount)
                .flatMap(cr -> {
                    cr.setCustomerDocument(card.getCustomerDocument());
                    cr.setNumberAccount(card.getNumberAccount());
                    cr.setLimitCredit(card.getLimitCredit());
                    cr.setRegistrationDate(LocalDateTime.now());

                    return cardService.updateCard(cr);
                }).map(cr ->ResponseEntity.status(HttpStatus.CREATED).body(cr))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
