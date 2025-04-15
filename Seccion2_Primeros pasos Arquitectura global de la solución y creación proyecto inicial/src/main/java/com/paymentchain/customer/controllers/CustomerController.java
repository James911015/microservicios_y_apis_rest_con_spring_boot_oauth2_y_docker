package com.paymentchain.customer.controllers;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping()
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> putCustomerById(@PathVariable String id, @RequestBody Customer body) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer body) {
        Customer save = repository.save(body);
        return ResponseEntity.ok(save);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String id) {
        return null;
    }


}
