package com.proyecto3.bankTransfer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Document(collection = "schema_bank.bankTransfer")
@Data
public class BankTransfer {

    @Id
    private String id;
    private String idOrigin;
    private String idDestination;
    private BigDecimal amount;

}
