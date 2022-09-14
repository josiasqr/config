package com.bootcamp.account.controllers;

import com.bootcamp.account.client.WClient;
import com.bootcamp.account.client.customer.Customer;
import com.bootcamp.account.domain.Account;
import com.bootcamp.account.domain.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {
  @Autowired
  private AccountService accountService;

  @GetMapping
  public ResponseEntity<List<Account>> listCustomers() {
    return ResponseEntity.ok(accountService.listAccounts());
  }

  @GetMapping("/customerDocument/{customerDocument}")
  public ResponseEntity<List<Account>> getCustomerDocument(@PathVariable("customerDocument") String document) {
    return ResponseEntity.ok(accountService.listAccountsCustomerDocument(document));
  }

  @GetMapping("/{numberAccount}")
  public ResponseEntity<Account> getNumberAccount(@PathVariable("numberAccount") Long numberAccount) {
    Account account = accountService.getNumberAccount(numberAccount);

    if (account == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(account);
  }

  @PostMapping
  public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
    }
    account.setNumberAccount(account.number());
    account.setType(account.getType().toUpperCase());
    account.setRegistrationDate(LocalDateTime.now());

    Account acc = accountService.createAccount(account);
    if (acc == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer no puede tener mas cuentas de Tipo = " + account.getType());
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(acc);
  }

  @PutMapping("/{numberAccount}")
  public ResponseEntity<Account> updateAccount(@Valid @RequestBody Account account, @PathVariable("numberAccount") Long numberAccount, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
    }

    Account acc = accountService.getNumberAccount(numberAccount);
    if (acc == null) {
      return ResponseEntity.notFound().build();
    }
    acc.setType(account.getType());
    acc.setTitular(account.getTitular());
    acc.setSignatories(account.getSignatories());
    acc.setBalance(account.getBalance());
    acc.setLimitTransaction(account.getLimitTransaction());
    acc.setRegistrationDate(LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.CREATED).body(accountService.updateAccount(acc));
  }

  @DeleteMapping("/{numberAccount}")
  public ResponseEntity<Void> deleteAccount(@PathVariable("numberAccount") Long numberAccount) {
    Account account = accountService.getNumberAccount(numberAccount);

    if (account == null) {
      return ResponseEntity.notFound().build();
    }
    accountService.deleteAccount(account);

    return ResponseEntity.ok().build();
  }
}
