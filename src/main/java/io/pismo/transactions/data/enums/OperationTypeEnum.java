package io.pismo.transactions.data.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Getter
public enum OperationTypeEnum {

    CASH,
    INSTALLMENT,
    WITHDRAW,
    PAYMENT,
    UNKNOWN;

    public static OperationTypeEnum fromMnemonic(final String mnemonic) {
        return Arrays.stream(values()).filter(o -> o.name().equals(mnemonic))
                .findFirst()
                .orElse(UNKNOWN);
    }


}
