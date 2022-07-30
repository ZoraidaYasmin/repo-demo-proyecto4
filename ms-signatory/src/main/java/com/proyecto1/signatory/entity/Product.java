package com.proyecto1.signatory.entity;

import lombok.Data;

@Data
public class Product {
	private String id;
    private int indProduct;
    private String descIndProduct;
    private int typeProduct;
    private String descTypeProduct;
    private Double amountPerMonth;
    private Double amountPerDay;
}
