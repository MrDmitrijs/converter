package com.currency.converter.exception;

public class FeeRepositoryException extends RuntimeException {

    public FeeRepositoryException(final String message, final Exception e) {
        super(message, e);
    }
}
