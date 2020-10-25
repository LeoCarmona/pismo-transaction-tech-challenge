package io.pismo.transactions.data.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pismo.transactions.data.entity.Account;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Data
public class AccountResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "User account ID", position = 1)
    private Long id;

    @JsonProperty("document_number")
    @ApiModelProperty(value = "User document number", position = 1)
    private String documentNumber;

    public AccountResponse(final Account account) {
        this.id = account.getId();
        this.documentNumber = account.getDocumentNumber();
    }

}
