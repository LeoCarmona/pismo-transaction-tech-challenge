package io.pismo.transactions.service;

import io.pismo.transactions.data.entity.OperationType;
import io.pismo.transactions.exceptions.PismoException;
import io.pismo.transactions.repository.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Service
public class OperationTypeService {

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    public OperationType findOperationTypeById(final Long id) {
        return this.findOperationTypeById(id, () -> new PismoException(HttpStatus.NOT_FOUND, "Operation type not found!"));
    }

    public OperationType findOperationTypeById(final Long id, final Supplier<PismoException> ex) {
        return this.operationTypeRepository.findById(id)
                .orElseThrow(ex);
    }

}
