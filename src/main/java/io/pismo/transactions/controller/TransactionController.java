package io.pismo.transactions.controller;

import io.pismo.transactions.aspect.LogExecutionTime;
import io.pismo.transactions.data.entity.Transaction;
import io.pismo.transactions.data.rest.transaction.TransactionRequest;
import io.pismo.transactions.data.rest.transaction.TransactionResponse;
import io.pismo.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @LogExecutionTime
    public TransactionResponse createTransaction(@Valid @RequestBody final TransactionRequest request) {
        final Transaction transaction = this.transactionService.createTransaction(request);

        return new TransactionResponse(transaction);
    }

    @GetMapping(path = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    public List<TransactionResponse> findAllTransactionsByAccountId(@PathVariable final Long accountId) {
        final List<Transaction> allTransactionsByAccount = this.transactionService.findAllTransactionsByAccountId(accountId);

        return allTransactionsByAccount.stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }

}
