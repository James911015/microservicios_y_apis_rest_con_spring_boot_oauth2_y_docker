package com.paymentchain.transaction.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private String reference;
    private String accountIban;
    private Date date;
    private Double amount;
    private Double fee;
    private String description;
    private String status;
    private String channel;
}
