package com.paymentchain.customer.repositories;

import com.paymentchain.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.code=?1")
    public Customer getByCode(String code);

    @Query("SELECT c FROM Customer c WHERE c.iban=?1")
    public Customer getByIban(String iban);
}

