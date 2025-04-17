package com.paymentchain.billing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private String number;
    private String detail;
    private Double amount;
}
