package com.bootcamp.transaction.card.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_card")
public class TCard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 5, max = 8, message = "code min 5 digits and max 8")
  @Column(unique = true, nullable = false)
  private String code;

  private Long numberAccount;

  @NotEmpty(message = "Operation can not be empty")
  private String operation;

  private Double amount;
  private LocalDateTime registrationDate;

  public boolean validate(Double currentBalance, Double amount) {
    if (currentBalance >= amount) {
      return true;
    }
    return false;
  }

  public String code() {
    Random r = new Random();
    Integer random = r.nextInt(999999);
    return random.toString();
  }
}
