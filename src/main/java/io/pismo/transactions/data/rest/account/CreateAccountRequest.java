package io.pismo.transactions.data.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pismo.transactions.data.entity.Account;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Data
public class CreateAccountRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Document number cannot be null")
    @NotBlank(message = "Document number cannot be blank")
    @JsonProperty("document_number")
    @ApiModelProperty(value = "User document number", position = 1)
    private String documentNumber;

    public Account toAccount() {
        final Account account = new Account();
        account.setDocumentNumber(documentNumber);

        return account;
    }

}
