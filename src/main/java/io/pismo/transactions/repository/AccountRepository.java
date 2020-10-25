package io.pismo.transactions.repository;

import io.pismo.transactions.data.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(final String documentNumber);

}
