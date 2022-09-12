package com.bootcamp.transaction.transfer.controller;

import com.bootcamp.transaction.credit.domain.TCredit;
import com.bootcamp.transaction.transfer.domain.Transfer;
import com.bootcamp.transaction.transfer.domain.TransferService;
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
@RequestMapping("/transfers")
public class TransferController {
    @Autowired
    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> listTransfers(){
        return ResponseEntity.ok(transferService.listTransfers());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Transfer> listTransactionsCode(@PathVariable("code") String code){
        Transfer transfer = transferService.getCodeTransfer(code);

        if(transfer == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transfer);
    }

    @PostMapping
    public ResponseEntity<Transfer> createTransaction(@Valid @RequestBody Transfer transfer, BindingResult errors){
        if (errors.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
        }
        transfer.setRegistrationDate(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.createTransfer(transfer));
    }
}
