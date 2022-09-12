package com.bootcamp.transaction.account.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "transaction_account")
public class TAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size( min = 5 , max = 8, message = "code min 5 digits and max 8")
    @NotEmpty(message = "code can not be empty")
    private String code;

    private Long numberAccount;

    @NotEmpty(message = "Operation can not be empty")
    private String operation;

    private Double amount;

    private LocalDateTime registrationDate;

    public boolean validate(Double currentBalance, Double amount, Double commission){
        if(currentBalance >= (amount + commission)){
            return true;
        }
        return false;
    }
}
