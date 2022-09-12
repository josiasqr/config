package com.bootcamp.credit.client;

import com.bootcamp.credit.client.customer.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.bootcamp.credit.util.Constants.BASE_URL;
import static com.bootcamp.credit.util.Constants.GET_CUSTOMER_DOCUMENT;
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
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
                    }
                    return cus.bodyToMono(Customer.class);
                });
    }
}
