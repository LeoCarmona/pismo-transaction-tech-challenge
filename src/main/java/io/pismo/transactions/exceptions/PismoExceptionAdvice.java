package io.pismo.transactions.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@RestControllerAdvice
public class PismoExceptionAdvice {

    @ExceptionHandler(value = PismoException.class)
    public ResponseEntity<Map<String, Object>> handlePismoException(final HttpServletRequest request, final PismoException e) {
        final Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", Instant.now());
        error.put("status", e.getStatusCode().value());
        error.put("error", e.getStatusCode().getReasonPhrase());
        error.put("message", e.getMessage());
        error.put("path", request.getRequestURI());

        return new ResponseEntity<>(error, e.getStatusCode());
    }

}
