package com.bootcamp.product.client.tcard;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@Data
public class TCard {
    private Integer id;
    private String code;
    private Long numberAccount;
    private String operation;
    private Double amount;
    private LocalDateTime registrationDate;
}
