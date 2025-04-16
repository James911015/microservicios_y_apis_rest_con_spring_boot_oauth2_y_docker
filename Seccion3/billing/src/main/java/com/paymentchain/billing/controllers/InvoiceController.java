package com.paymentchain.billing.controllers;

import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceRepository repository;

    @GetMapping()
    public ResponseEntity<List<Invoice>> getAllInvoice() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = repository.findById(id);
        if (invoice.isPresent()) {
            return new ResponseEntity<>(invoice.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoiceById(@PathVariable Long id, @RequestBody Invoice body) {
        Optional<Invoice> invoice = repository.findById(id);
        if (invoice.isPresent()) {
            Invoice newInvoice = invoice.get();
            newInvoice.setAmount(body.getAmount());
            newInvoice.setDetail(body.getDetail());
            newInvoice.setNumber(body.getNumber());
            newInvoice.setCustomerId(body.getCustomerId());
            Invoice update = repository.save(newInvoice);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice body) {
        return new ResponseEntity<>(repository.save(body), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
