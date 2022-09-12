package com.bootcamp.transaction.transfer.service;

import com.bootcamp.transaction.transfer.domain.Transfer;
import com.bootcamp.transaction.transfer.domain.TransferService;
import com.bootcamp.transaction.transfer.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InTransferService implements TransferService {
    @Autowired
    private TransferRepository transferRepository;

    public InTransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public List<Transfer> listTransfers() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer getCodeTransfer(String code) {
        return transferRepository.findByCode(code);
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }
}
