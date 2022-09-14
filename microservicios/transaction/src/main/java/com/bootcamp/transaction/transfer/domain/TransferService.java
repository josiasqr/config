package com.bootcamp.transaction.transfer.domain;

import java.util.List;

public interface TransferService {
  List<Transfer> listTransfers();

  Transfer getCodeTransfer(String code);

  Transfer createTransfer(Transfer transfer);
}
