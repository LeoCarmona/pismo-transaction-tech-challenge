package io.pismo.transactions.configuration;

import io.pismo.transactions.data.entity.OperationType;
import io.pismo.transactions.repository.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Configuration
public class OperationTypeConfig {

    @Autowired
    private OperationTypeRepository repository;

    @PostConstruct
    public void init() {
        repository.saveAll(Arrays.asList(
                OperationType.builder().id(1L).mnemonic("CASH").description("Compra à Vista").isAmountPositive(false).build(),
                OperationType.builder().id(2L).mnemonic("INSTALLMENT").description("Compra Parcelada").isAmountPositive(false).build(),
                OperationType.builder().id(3L).mnemonic("WITHDRAW").description("Saque").isAmountPositive(false).build(),
                OperationType.builder().id(4L).mnemonic("PAYMENT").description("Pagamento").isAmountPositive(true).build()
        ));
    }

}
