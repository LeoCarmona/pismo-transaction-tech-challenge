package io.pismo.transactions.data.rest.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pismo.transactions.data.entity.Transaction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Data
public class TransactionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Transaction mnemonic", position = 1)
    private String mnemonic;

    @ApiModelProperty(value = "Transaction description", position = 2)
    private String description;

    @ApiModelProperty(value = "Transaction amount", position = 3)
    private BigDecimal amount;

    @JsonProperty("event_date")
    @ApiModelProperty(value = "When transaction ocurred", position = 4)
    private Instant eventDate;

    public TransactionResponse(final Transaction transaction) {
        this.mnemonic = transaction.getOperationType().getMnemonic();
        this.description = transaction.getOperationType().getDescription();
        this.amount = transaction.getAmount();
        this.eventDate= transaction.getEventDate();
    }

}
