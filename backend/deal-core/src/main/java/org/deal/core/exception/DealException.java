package org.deal.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DealException extends RuntimeException {
    private final HttpStatus status;

    public DealException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
