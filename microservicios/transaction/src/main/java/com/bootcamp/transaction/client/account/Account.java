package com.bootcamp.transaction.client.account;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

import static com.bootcamp.transaction.util.Constants.ACCOUNT_TYPE_CORRIENTE;

@Data
@ToString
@Builder
@EqualsAndHashCode
public class Account {
  private String id;
  private String customerDocument;
  private String type;
  private List<String> titular;
  private List<String> signatories;
  private Long numberAccount;
  private Double balance;
  @Transient
  private Double commission;
  private Integer limitTransaction;
  private LocalDateTime registrationDate;

  public Double getCommission() {
    if (this.getType().equals(ACCOUNT_TYPE_CORRIENTE)) {
      return 7.5;
    }
    return 0.0;
  }
}
