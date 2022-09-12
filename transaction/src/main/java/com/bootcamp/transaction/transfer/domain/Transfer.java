package com.bootcamp.transaction.transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size( min = 5 , max = 8, message = "code min 5 digits and max 8")
    @NotEmpty(message = "code can not be empty")
    private String code;

    private Long origin;
    private Long destiny;
    private Double amount;
    private LocalDateTime registrationDate;

    public boolean validate(Double currentBalance, Double amount){
        if(currentBalance <= amount){
            return true;
        }
        return false;
    }
}
