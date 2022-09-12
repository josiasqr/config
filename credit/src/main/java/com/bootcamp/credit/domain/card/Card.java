package com.bootcamp.credit.domain.card;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Data @ToString @Builder
@Document(collection = "card")
public class Card {
    private String id;
    @NotEmpty(message = "customerDocument not be empty")
    private String customerDocument;
    private Long numberAccount;
    private Double limitCredit;
    private LocalDateTime registrationDate;
}