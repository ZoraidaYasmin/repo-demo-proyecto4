package com.project4.app.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project4.app.entity.VirtualWalletEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VirtualWalletProducer {
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void sendVirtualWalletEvent(VirtualWalletEvent vw) throws JsonProcessingException {
		
		Integer key = vw.getVirtualWalletId();
		String value = objectMapper.writeValueAsString(vw);
		
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key, value, result);
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
				
			}
		});
	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error al enviar el mensaje, el error es {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable){
			log.error("Error {}", throwable.getMessage());
		}
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Mensaje enviado satisfactoriamente con la key: {} y el valor: {} en la particion {}", key, value, result.getRecordMetadata().partition());
		
	}
	
}
