package com.bootcamp.transaction.client.card;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class Card {
  private String id;
  private String customerDocument;
  private Long numberAccount;
  private Double limitCredit;
  private LocalDateTime registrationDate;
}
