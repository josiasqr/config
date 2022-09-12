package com.bootcamp.credit.domain.credit;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

import static com.bootcamp.credit.util.Constants.CUSTOMER_TYPE_PERSONAL;

@Data @ToString @Builder
@Document(collection = "credit")
public class Credit {
    private String id;
    @NotEmpty(message = "CustomerDocument not be empty")
    private String customerDocument;
    private Double amount;
    @NotEmpty(message = "Status not be empty")
    private String status;
    private LocalDateTime expirationDate;
    private LocalDateTime registrationDate;

    public Boolean validate(String typeCustomer, Long countCredits){
        if(typeCustomer.equals(CUSTOMER_TYPE_PERSONAL) && countCredits > 0){
            return true;
        }
        return false;
    }
}
