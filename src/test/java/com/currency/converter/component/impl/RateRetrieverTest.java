package com.currency.converter.component.impl;

import static com.currency.converter.domain.Currency.EUR;
import static com.currency.converter.domain.Currency.USD;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.currency.converter.domain.ExchangeRateResponse;
import com.currency.converter.exception.ExchangeRateApiException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class RateRetrieverTest {

    private static final String EXCHANGE_RATE_API_URI = "https://api.exchangeratesapi.io/latest?base={currencyFrom}&symbols={currencyTo}";

    @Test
    void getRate_happyPath() {
        final ExchangeRateResponse expected = new ExchangeRateResponse();
        assertEquals(expected.getRate(), getTestedObject(new ResponseEntity<>(expected, HttpStatus.OK)).getRate(EUR, USD));
    }

    @Test
    void getRate_throwsException_bodyIsNull() {
        final ExchangeRateApiException exception = assertThrows(ExchangeRateApiException.class,
                () -> getTestedObject(new ResponseEntity<>(null, HttpStatus.OK)).getRate(EUR, USD));
        assertTrue(exception.getMessage().contains("Failed to retrieve exchange rate from external api.Body is null!"));
    }

    @Test
    void getRate_throwsException_statusIsNotOK() {
        final ExchangeRateApiException exception = assertThrows(ExchangeRateApiException.class,
                () -> getTestedObject(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)).getRate(EUR, USD));
        assertTrue(exception.getMessage().contains("Failed to retrieve exchange rate from external api." + format("Received status code {}", HttpStatus.BAD_REQUEST)));
    }

    private static RateRetriever getTestedObject(final ResponseEntity<ExchangeRateResponse> responseEntity) {
        final RestTemplate restTemplate = mock(RestTemplate.class);
        final Map<String, Object> params = new HashMap<>();
        params.put("currencyFrom", EUR);
        params.put("currencyTo", USD);
        when(restTemplate.getForEntity(EXCHANGE_RATE_API_URI, ExchangeRateResponse.class, params)).thenReturn(responseEntity);
        return new RateRetriever(restTemplate, EXCHANGE_RATE_API_URI);
    }
}