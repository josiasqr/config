package com.bootcamp.account.client;

import com.bootcamp.account.client.TAccount.TAccount;
import com.bootcamp.account.client.customer.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.bootcamp.account.util.Constants.*;

@Component
public class WClient {
    private final WebClient webClient;

    public WClient(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(BASE_URL).build();
    }

    /*
     * Microservice Customer
     * */
    public Customer getCustomer(String document) {
        return webClient.get().uri(GET_CUSTOMER_DOCUMENT, document)
                .exchangeToMono(cus ->{
                    if(cus.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with Id = " + document);
                    }
                    return cus.bodyToMono(Customer.class);
                })
                .block();
    }

    /*
     * Microservice Transaction
     * */
    public Mono<TAccount> postTAccount(TAccount tAccount) {
        return webClient.post()
                .uri(POST_TRANSACTION_ACCOUNT)
                .body(Mono.just(tAccount), TAccount.class)
                .retrieve()
                .bodyToMono(TAccount.class);
    }
}
