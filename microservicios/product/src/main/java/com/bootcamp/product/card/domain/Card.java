package com.bootcamp.product.card.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Random;

@Data @ToString @Builder
@Document(collection = "card")
public class Card {
    private String id;

    @NotEmpty(message = "customerDocument not be empty")
    private String customerDocument;

    @Indexed(name = "number_account_index_unique", unique = true)
    private Long numberAccount;
    private Double limitCredit;
    private LocalDateTime registrationDate;

    public Long number(){
        Random random = new Random();
        Long numberRandom = random.nextLong();

        if(numberRandom>0){
            return numberRandom;
        }else{
            return numberRandom*-1;
        }
    }
}