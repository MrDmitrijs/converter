package com.currency.converter.exception;

public class ExchangeRateApiException extends RuntimeException {
    private static final String MESSAGE = "Failed to retrieve exchange rate from external api.";

    public ExchangeRateApiException(final String message) {
        super(MESSAGE + message);
    }

    public ExchangeRateApiException(final Exception e) {
        super(MESSAGE, e);
    }
}
