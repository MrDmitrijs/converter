package com.currency.converter.component.impl;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.ExchangeRateResponse;
import com.currency.converter.exception.ExchangeRateApiException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;

@Component
public class RateRetriever {

    private static final Logger LOG = getLogger(RateRetriever.class);

    private RestTemplate client;
    private final String exchangeRateApiUri;

    @Autowired
    public RateRetriever(@NonNull final RestTemplate client,
                         @NonNull @Value("${exchange.rate.api.url}") final String exchangeRateApiUri) {
        this.client = client;
        this.exchangeRateApiUri = exchangeRateApiUri;
    }

    public BigDecimal getRate(final Currency currencyFrom, final Currency currencyTo) {
        final Map<String, Object> params = new HashMap<>();
        params.put("currencyFrom", currencyFrom);
        params.put("currencyTo", currencyTo);
        try {
            LOG.info("Calling exchange rate api.");
            final ResponseEntity<ExchangeRateResponse> responseEntity = client.getForEntity(exchangeRateApiUri, ExchangeRateResponse.class, params);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == OK) {
                final ExchangeRateResponse body = responseEntity.getBody();
                if (body != null) {
                    LOG.info("Received response from exchange rate api: {}", body);
                    return body.getRate();
                } else {
                    throw new ExchangeRateApiException("Body is null!");
                }
            } else {
                //noinspection MalformedFormatString
                throw new ExchangeRateApiException(format("Received status code {}", statusCode));
            }
        } catch (final RestClientException e) {
            throw new ExchangeRateApiException(e);
        }
    }
}
