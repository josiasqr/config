package com.bootcamp.customer.domain;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data @ToString @Builder @AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {
    private String id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    //@Size( min = 8 , max = 11, message = "Document min 8 digits and max 11")
    //@NotEmpty(message = "Document can not be empty")
    @Indexed(name = "document_index_unique", unique = true)
    private String document;

    private String type;
    private LocalDateTime registrationDate;
}
