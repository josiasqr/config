package com.bootcamp.transaction.credit.repository;

import com.bootcamp.transaction.credit.domain.TCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCreditRepository extends JpaRepository<TCredit, Integer> {
  TCredit findByCode(String code);

  List<TCredit> findByIdCredit(String idCredit);
}
