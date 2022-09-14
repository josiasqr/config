package com.bootcamp.transaction.notifications.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification")
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 5, max = 8, message = "code min 5 digits and max 8")
  @Column(unique = true, nullable = false)
  private String code;

  @Transient
  private String customer;

  private Long numberAccount;

  @NotEmpty(message = "Operation can not be empty")
  @Column(nullable = false)
  private String operation;

  private Double amount;
  private LocalDateTime registrationDate;
}
