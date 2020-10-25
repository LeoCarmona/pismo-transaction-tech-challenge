package io.pismo.transactions.service;

import io.pismo.transactions.data.entity.OperationType;
import io.pismo.transactions.data.entity.Transaction;
import io.pismo.transactions.data.rest.transaction.TransactionRequest;
import io.pismo.transactions.data.rest.transaction.TransactionResponse;
import io.pismo.transactions.exceptions.PismoException;
import io.pismo.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationTypeService operationTypeService;

    public Transaction createTransaction(final TransactionRequest request) {
        this.checkIfAccountExists(request);

        final OperationType operationType = this.findOperationType(request);
        final Transaction transaction = this.buildTransaction(request, operationType);

        return this.transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransactionsByAccountId(final Long accountId) {
        return this.transactionRepository.findAllTransactionByAccountIdOrderByEventDateDesc(accountId);
    }

    private void checkIfAccountExists(final TransactionRequest request) {
        if (!this.accountService.existsAccountById(request.getAccountId())) {
            throw new PismoException(HttpStatus.BAD_REQUEST, "Account not found!");
        }
    }

    private OperationType findOperationType(final TransactionRequest request) {
        return this.operationTypeService.findOperationTypeById(request.getOperationTypeId(), () -> new PismoException(HttpStatus.BAD_REQUEST, "Operation Type not found!"));
    }

    private Transaction buildTransaction(final TransactionRequest request, final OperationType operationType) {
        final Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setOperationType(operationType);
        transaction.setAmount(request.getAmount());
        transaction.setEventDate(Instant.now());

        this.adjustAmount(transaction, operationType);

        return transaction;
    }

    private void adjustAmount(final Transaction transaction, final OperationType operationType) {
        final boolean isAmountPositive = transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0;
        final boolean mustNegateAmountValue = operationType.isAmountPositive() != isAmountPositive;

        BigDecimal fixedAmount = transaction.getAmount().setScale(2, RoundingMode.HALF_EVEN);

        if (mustNegateAmountValue) {
            fixedAmount = fixedAmount.negate();
        }

        transaction.setAmount(fixedAmount);
    }

}
