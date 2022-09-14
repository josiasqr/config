package com.bootcamp.product.client.tcredit;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Data
public class TCredit {
  private Integer id;
  private String code;
  private String idCredit;
  private String operation;
  private Double amount;
  private LocalDateTime registrationDate;
}
