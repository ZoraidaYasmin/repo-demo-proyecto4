package com.proyecto1.transaction.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.proyecto1.transaction.client.DebitCardClient;
import com.proyecto1.transaction.entity.Transaction;
import com.proyecto1.transaction.entity.VirtualWalletEvent;
import com.proyecto1.transaction.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class VirtualWalletConsumer {
	
	@Autowired
	DebitCardClient debitCardClient;
	
	@Autowired
	TransactionService transactionService;
	
	@KafkaListener(topics = {"virtual-wallet-events"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
		log.info("ConsumerRecord: {}", consumerRecord);
		VirtualWalletEvent vwEvent = new Gson().fromJson(consumerRecord.value(), VirtualWalletEvent.class);
		if(vwEvent.getCardNumberEmisor()!=null) {
			updateEmisor(vwEvent);
		}
		if(vwEvent.getCardNumberReceptor()!=null) {
			updateReceptor(vwEvent);
		} 
		
		
	}
	
	private void updateEmisor(VirtualWalletEvent vwEvent) {
		
		
		Flux<Transaction> tra = debitCardClient.getPrincipalDebitAccount(vwEvent.getCardNumberEmisor()).flatMap(debitCard -> {
			if (debitCard.getTrans().getAvailableBalance().compareTo(vwEvent.getAmount()) >= 0) {
				debitCard.getTrans().setAvailableBalance(debitCard.getTrans().getAvailableBalance().subtract(vwEvent.getAmount()));
				return transactionService.update(debitCard.getTrans(), debitCard.getTrans().getId());
			} else {
				return Mono.error(new Throwable());
			}
		});
		
		tra.subscribe(t -> log.info("Entro a funcion updateEmisor valor {}", t.toString()));
	}
	
	private void updateReceptor(VirtualWalletEvent vwEvent) {
		Flux<Transaction> tra = debitCardClient.getPrincipalDebitAccount(vwEvent.getCardNumberReceptor()).flatMap(debitCard -> {
				debitCard.getTrans().setAvailableBalance(vwEvent.getAmount().add(debitCard.getTrans().getAvailableBalance()));
				return transactionService.update(debitCard.getTrans(), debitCard.getTrans().getId());	
		});
		
		tra.subscribe(t -> log.info("Entro a funcion updateReceptor valor {}", t.toString()));
	}
}
