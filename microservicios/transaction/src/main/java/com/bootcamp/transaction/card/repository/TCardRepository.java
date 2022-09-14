package com.bootcamp.transaction.card.repository;

import com.bootcamp.transaction.card.domain.TCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCardRepository extends JpaRepository<TCard, Integer> {
  TCard findByCode(String code);

  List<TCard> findByNumberAccount(Long number);
}
