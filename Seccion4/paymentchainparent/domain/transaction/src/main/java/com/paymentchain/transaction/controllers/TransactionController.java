package com.paymentchain.transaction.controllers;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.repositories.TransactionRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionRepository repository;

    private final WebClient.Builder webClient;

    HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    public TransactionController(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder;
    }


    @GetMapping()
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@RequestParam Long transactionId) {
        Optional<Transaction> customer = repository.findById(transactionId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/iban/{ibann}")
    public List<Transaction> getTransactionByIban(@RequestParam String ibann) {
        return repository.getByIban(ibann);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransactionById(@PathVariable Long id, @RequestBody Transaction body) {
        Optional<Transaction> transaction = repository.findById(id);
        if (transaction.isPresent()) {
            Transaction newTransaction = transaction.get();
            newTransaction.setReference(body.getReference());
            newTransaction.setAccountIban(body.getAccountIban());
            newTransaction.setDate(body.getDate());
            newTransaction.setAmount(body.getAmount());
            newTransaction.setFee(body.getFee());
            newTransaction.setDescription(body.getDescription());
            newTransaction.setStatus(body.getStatus());
            newTransaction.setChannel(body.getChannel());
            Transaction update = repository.save(newTransaction);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction body) {
        Transaction saved = repository.save(body);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
