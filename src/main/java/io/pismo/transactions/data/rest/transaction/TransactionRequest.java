package io.pismo.transactions.data.rest.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Data
public class TransactionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty("account_id")
    @ApiModelProperty(value = "User account ID", position = 1)
    private Long accountId;

    @NotNull
    @JsonProperty("operation_type_id")
    @ApiModelProperty(value = "Operation type ID for this transaction", position = 2)
    private Long operationTypeId;

    @NotNull
    @ApiModelProperty(value = "Transaction amount", position = 3)
    private BigDecimal amount;

}
