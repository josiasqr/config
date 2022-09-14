package com.bootcamp.transaction.account.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_account")
public class TAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 5, max = 8, message = "code min 5 digits and max 8")
  @Column(unique = true, nullable = false)
  private String code;

  @Column(nullable = false)
  private Long numberAccount;

  @NotEmpty(message = "Operation can not be empty")
  @Column(nullable = false)
  private String operation;

  @Column(nullable = false)
  private Double amount;

  private LocalDateTime registrationDate;

  public boolean validate(Double currentBalance, Double amount, Double commission) {
    if (currentBalance >= (amount + commission)) {
      return true;
    }
    return false;
  }

  public String code() {
    Random r = new Random();
    Integer random = r.nextInt(999999);
    return random.toString();
  }

  public Boolean commission(Integer limit, Integer countTransactions) {
    if (limit < countTransactions) {
      return true;
    }

    return false;
  }

  public LocalDateTime firstDayWeek() {
    LocalDate today = LocalDate.now();
    LocalDate firstDayWeek = today.with(TemporalAdjusters.firstDayOfMonth());
    LocalDateTime parse = LocalDateTime.parse(firstDayWeek + "T00:00:00.00");

    return parse;
  }
}
