package com.bootcamp.account.client.TAccount;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Random;

@Builder
@ToString
@Data
public class TAccount {
    private Integer id;
    private String code;
    private Long numberAccount;
    private String operation;
    private Double amount;
    private LocalDateTime registrationDate;
}
