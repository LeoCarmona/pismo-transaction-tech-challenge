package io.pismo.transactions.repository;

import io.pismo.transactions.data.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    List<Transaction> findAllTransactionByAccountIdOrderByEventDateDesc(final Long accountId);

}
