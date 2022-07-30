package com.proyecto1.customer.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Signatory {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String docNumber;
    private String transactionId;
}
