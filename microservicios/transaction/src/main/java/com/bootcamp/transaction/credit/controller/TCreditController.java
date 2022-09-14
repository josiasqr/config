package com.bootcamp.transaction.credit.controller;

import com.bootcamp.transaction.credit.domain.TCredit;
import com.bootcamp.transaction.credit.domain.TCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactionCredit")
public class TCreditController {
  @Autowired
  private TCreditService tCreditService;

  public TCreditController(TCreditService tCreditService) {
    this.tCreditService = tCreditService;
  }

  @GetMapping
  public ResponseEntity<List<TCredit>> listTransactions() {
    return ResponseEntity.ok(tCreditService.listTransactions());
  }

  @GetMapping("/idCredit/{idCredit}")
  public ResponseEntity<List<TCredit>> listTransactionsIdCredit(@PathVariable("idCredit") String idCredit) {
    return ResponseEntity.ok(tCreditService.listTransactionIdCredits(idCredit));
  }

  @GetMapping("/{code}")
  public ResponseEntity<TCredit> listTransactionsCode(@PathVariable("code") String code) {
    TCredit tCredit = tCreditService.getCodeTransaction(code);

    if (tCredit == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(tCredit);
  }

  @PostMapping
  public ResponseEntity<TCredit> createTransaction(@Valid @RequestBody TCredit tCredit, BindingResult errors) {
    System.out.println("tCredit.toString() = " + tCredit.toString());
    if (errors.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
    }
    tCredit.setCode(tCredit.code());
    tCredit.setRegistrationDate(LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.CREATED).body(tCreditService.createTransaction(tCredit));
  }
}
