package com.project4.app.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class VirtualWallet {
	
	@Id
	private String id;
	private String dni;
	private String cellphone;
	private String operation;
	private BigDecimal amount;
	private String cardNumber;
}
