package com.bootcamp.transaction.account.controller;

import com.bootcamp.transaction.account.domain.TAccount;
import com.bootcamp.transaction.account.domain.TAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactionAccount")
public class TAccountController {
    @Autowired
    private TAccountService tAccountService;

    public TAccountController(TAccountService tAccountService) {
        this.tAccountService = tAccountService;
    }

    @GetMapping
    public ResponseEntity<List<TAccount>> listTransactions(){
        return ResponseEntity.ok(tAccountService.listTransactions());
    }

    @GetMapping("/listTransactionsOfWeek")
    public ResponseEntity<List<TAccount>> listTransactionsOfWeek(){
        TAccount tAccount = new TAccount();

        return ResponseEntity.ok(tAccountService.listTransactionsOfWeek(3927198245224716L, tAccount.firstDayWeek(), LocalDateTime.now()));
    }

    @GetMapping("/numberAccount/{number}")
    public ResponseEntity<List<TAccount>> listTransactionsNumberAccount(@PathVariable("number") Long number){
        return ResponseEntity.ok(tAccountService.listTransactionNumberAccount(number));
    }

    @GetMapping("/{code}")
    public ResponseEntity<TAccount> listTransactionsCode(@PathVariable("code") String code){
        TAccount tAccount = tAccountService.getCodeTransaction(code);

        if(tAccount == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tAccount);
    }

    @PostMapping
    public ResponseEntity<TAccount> createTransaction(@Valid @RequestBody TAccount tAccount, BindingResult errors){
        if (errors.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
        }
        tAccount.setCode(tAccount.code());
        tAccount.setRegistrationDate(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(tAccountService.createTransaction(tAccount));
    }
}
