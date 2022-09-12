package com.bootcamp.transaction.card.controller;

import com.bootcamp.transaction.card.domain.TCard;
import com.bootcamp.transaction.card.domain.TCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactionCard")
public class TCardController {
    @Autowired
    private TCardService tCardService;

    public TCardController(TCardService tCardService) {
        this.tCardService = tCardService;
    }

    @GetMapping
    public ResponseEntity<List<TCard>> listTransactions(){
        return ResponseEntity.ok(tCardService.listTransactions());
    }

    @GetMapping("/numberAccount/{number}")
    public ResponseEntity<List<TCard>> listTransactionsNumberAccount(@PathVariable("number") Long number){
        return ResponseEntity.ok(tCardService.listTransactionNumberAccount(number));
    }

    @GetMapping("/{code}")
    public ResponseEntity<TCard> listTransactionsCode(@PathVariable("code") String code){
        TCard tCard = tCardService.getCodeTransaction(code);

        if(tCard == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tCard);
    }

    @PostMapping
    public ResponseEntity<TCard> createTransaction(@Valid @RequestBody TCard tCard, BindingResult errors){
        if (errors.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
        }
        tCard.setRegistrationDate(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(tCardService.createTransaction(tCard));
    }
}
