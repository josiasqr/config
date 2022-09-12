package com.bootcamp.transaction.client;

import com.bootcamp.transaction.client.account.Account;
import com.bootcamp.transaction.client.card.Card;
import com.bootcamp.transaction.client.credit.Credit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.bootcamp.transaction.util.Constants.*;

@Component
public class WClient {
    private final WebClient webClient;

    public WClient(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(BASE_URL).build();
    }

    /*
     * Microservice Account
     * */
    public Account getAccount(Long number) {
        return webClient.get().uri(GET_ACCOUNT_NUMBER_ACCOUNT, number)
                .exchangeToMono(cus ->{
                    if(cus.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Bank not found");
                    }
                    return cus.bodyToMono(Account.class);
                }).block();
    }
    public Account putAccount(Long number, Account account) {
        return webClient.put()
                .uri(PUT_ACCOUNT, number)
                .body(Mono.just(account), Account.class)
                .retrieve()
                .bodyToMono(Account.class)
                .block();
    }

    /*
     * Microservice Credit
     * */
    public Credit getCredit(String idCredit) {
        return webClient.get().uri(GET_CREDIT_ID, idCredit)
                .exchangeToMono(cus ->{
                    if(cus.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found");
                    }
                    return cus.bodyToMono(Credit.class);
                }).block();
    }
    public Credit putCredit(String idCredit, Credit credit) {
        return webClient.put()
                .uri(PUT_CREDIT, idCredit)
                .body(Mono.just(credit), Credit.class)
                .retrieve()
                .bodyToMono(Credit.class)
                .block();
    }

    /*
     * Microservice Card
     * */
    public Card getCard(Long number) {
        return webClient.get().uri(GET_CARD_NUMBER_ACCOUNT, number)
                .exchangeToMono(card ->{
                    if(card.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
                    }
                    return card.bodyToMono(Card.class);
                }).block();
    }
    public Card putCard(Long number, Card card) {
        return webClient.put()
                .uri(PUT_CARD, number)
                .body(Mono.just(card), Credit.class)
                .retrieve()
                .bodyToMono(Card.class)
                .block();
    }
}
