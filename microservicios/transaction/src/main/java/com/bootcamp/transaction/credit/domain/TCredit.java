package com.bootcamp.transaction.credit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Random;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "transaction_credit")
public class TCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size( min = 5 , max = 8, message = "code min 5 digits and max 8")
    @Column(unique = true)
    private String code;

    @NotEmpty(message = "idCredit can not be empty")
    private String idCredit;

    @NotEmpty(message = "Operation can not be empty")
    private String operation;

    private Double amount;
    private LocalDateTime registrationDate;

    public boolean validate(Double currentBalance, Double amount){
        if(currentBalance >= amount){
            return true;
        }
        return false;
    }

    public String code(){
        Random r = new Random();
        Integer random = r.nextInt(999999);
        return random.toString();
    }
}
