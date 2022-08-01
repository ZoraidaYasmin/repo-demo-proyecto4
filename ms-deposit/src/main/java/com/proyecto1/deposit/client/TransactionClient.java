package com.proyecto1.deposit.client;

import com.proyecto1.deposit.entity.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TransactionClient {
    private WebClient client = WebClient.create("http://transaction-service:9004/transaction");

    public Mono<Transaction> getTransactionWithDetails(String id){
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findByIdWithCustomer/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(Transaction.class);
    };
    
    public Mono<Transaction> updateTransaction(Transaction transaction){
        return client.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/update/{id}")
                        .build(transaction.getId())
                )
                .bodyValue(transaction)
                .retrieve()
                .bodyToMono(Transaction.class);
    };
}
