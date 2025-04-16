package org.deal.core.exception;

import org.deal.core.response.DealResponse;
import org.springframework.http.HttpStatus;

public class BaseExceptionHandler {

    public DealResponse<?> handleDealException(final DealException exception) {
        return DealResponse.failureResponse(new DealError(exception.getMessage()), exception.getStatus());
    }

    public DealResponse<?> handle(final Exception exception) {
        return DealResponse.failureResponse(new DealError(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
