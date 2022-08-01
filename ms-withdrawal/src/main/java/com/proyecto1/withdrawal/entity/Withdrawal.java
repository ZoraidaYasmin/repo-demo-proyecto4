package com.proyecto1.withdrawal.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "schema_with.withdrawals")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Withdrawal {

    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private BigDecimal withdrawalAmount;
    private String description;
    private String transactionId;
}
