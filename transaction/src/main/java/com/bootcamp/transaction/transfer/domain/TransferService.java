package com.bootcamp.transaction.transfer.domain;

import com.bootcamp.transaction.credit.domain.TCredit;

import java.util.List;

public interface TransferService {
    List<Transfer> listTransfers();
    Transfer getCodeTransfer(String code);
    Transfer createTransfer(Transfer transfer);
}
