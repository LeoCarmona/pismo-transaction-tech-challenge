package io.pismo.transactions.repository;

import io.pismo.transactions.data.entity.OperationType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Repository
public interface OperationTypeRepository extends PagingAndSortingRepository<OperationType, Long> {

    Optional<OperationType> findOperationTypeByMnemonic(final String mnemonic);

}
