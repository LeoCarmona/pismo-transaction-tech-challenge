package io.pismo.transactions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Getter
public class PismoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus statusCode;

    public PismoException(final HttpStatus statusCode, final String message, final Throwable cause) {
        super(message == null ? "" : message, cause);
        this.statusCode = statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : statusCode;
    }

    public PismoException(final HttpStatus statusCode, final String errorMessage) {
        this(statusCode, errorMessage, null);
    }

    public PismoException(final HttpStatus statusCode) {
        this(statusCode, null, null);
    }

}
