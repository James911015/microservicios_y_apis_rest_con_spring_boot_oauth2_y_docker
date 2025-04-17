package com.paymentchain.customer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerProductId;

    @Transient
    private String productName;

    @JsonIgnore// Es necesario para no llegar a un vacion infinito de recursivo con la mismaclase
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customerId;
}
