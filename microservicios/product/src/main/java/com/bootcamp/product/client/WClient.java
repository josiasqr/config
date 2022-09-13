package com.bootcamp.product.client;

import com.bootcamp.product.client.tcard.TCard;
import com.bootcamp.product.client.customer.Customer;
import com.bootcamp.product.client.tcredit.TCredit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.bootcamp.product.util.Constants.*;

@Component
public class WClient {
    private final WebClient webClient;

    public WClient(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(BASE_URL).build();
    }

    /*
    * Microservice Customer
    * */
    public Mono<Customer> getCustomer(String document) {
        return webClient.get().uri(GET_CUSTOMER_DOCUMENT, document)
                .exchangeToMono(cus ->{
                    if(cus.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with Id = " + document);
                    }
                    return cus.bodyToMono(Customer.class);
                });
    }

    /*
     * Microservice Transaction
     * */
    public Mono<TCard> postTCard(TCard tCard) {
        return webClient.post()
                .uri(POST_TRANSACTION_CARD)
                .body(Mono.just(tCard), TCard.class)
                .retrieve()
                .bodyToMono(TCard.class);
    }

    public Mono<TCredit> postTCredit(TCredit tCredit) {
        return webClient.post()
                .uri(POST_TRANSACTION_CREDIT)
                .body(Mono.just(tCredit), TCredit.class)
                .retrieve()
                .bodyToMono(TCredit.class);
    }
}
