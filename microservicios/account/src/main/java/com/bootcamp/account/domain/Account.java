package com.bootcamp.account.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.bootcamp.account.util.Constants.CUSTOMER_TYPE_EMPRESARIAL;
import static com.bootcamp.account.util.Constants.CUSTOMER_TYPE_PERSONAL;

@Data @ToString @Builder
@Document(collection = "account")
public class Account {
    private String id;

    @NotEmpty(message = "CustomerDocument Customer can not be empty")
    private String customerDocument;

    @NotEmpty(message = "Type can not be empty")
    private String type;

    @NotEmpty(message = "Titular can not be empty")
    private List<String> titular;

    private List<String> signatories;

    @Indexed(name = "number_account_index_unique", unique = true)
    private Long numberAccount;
    private Double balance;
    @Transient
    private Double commission;
    private Integer limitTransaction;
    private LocalDateTime registrationDate;

    public Double getCommission(){
        if(this.getType().equals("CORRIENTE")){
            return 7.5;
        }
        return 0.0;
    }

    public Boolean validate(String typeCustomer, Integer count){
        if(typeCustomer.equals(CUSTOMER_TYPE_EMPRESARIAL) && this.getType().equals("CORRIENTE")){
            return true;
        }
        else if(typeCustomer.equals(CUSTOMER_TYPE_PERSONAL)){
            if(count.equals(0)){
                return true;
            }
        }
        return false;
    }

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
