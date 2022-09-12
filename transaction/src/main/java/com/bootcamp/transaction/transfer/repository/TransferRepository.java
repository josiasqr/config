package com.bootcamp.transaction.transfer.repository;

import com.bootcamp.transaction.credit.domain.TCredit;
import com.bootcamp.transaction.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    Transfer findByCode(String code);
}
