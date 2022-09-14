package com.bootcamp.transaction.account.repository;

import com.bootcamp.transaction.account.domain.TAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TAccountRepository extends JpaRepository<TAccount, Integer> {
  TAccount findByCode(String code);

  List<TAccount> findByNumberAccount(Long number);

  List<TAccount> findAllByNumberAccountAndRegistrationDateBetween(Long number, LocalDateTime dateStart, LocalDateTime dateEnd);
}
