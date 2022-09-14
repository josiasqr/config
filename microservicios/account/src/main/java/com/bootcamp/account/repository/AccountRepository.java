package com.bootcamp.account.repository;

import com.bootcamp.account.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, Integer> {
  List<Account> findByCustomerDocument(String document);

  Account findByNumberAccount(Long numberAccount);
}
