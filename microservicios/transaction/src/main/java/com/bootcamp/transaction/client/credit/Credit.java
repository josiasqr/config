package com.bootcamp.transaction.client.credit;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@EqualsAndHashCode
public class Credit {
  private String id;
  private String customerDocument;
  private Double amount;
  private String status;
  private LocalDateTime expirationDate;
  private LocalDateTime registrationDate;
}
