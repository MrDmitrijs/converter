package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    private BigDecimal rate;

    @JsonProperty("rates")
    private void unnestRates(final Map<String, Object> rates) {
        double value = (double) rates.entrySet().iterator().next().getValue();
        this.rate = new BigDecimal(value);
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "rate=" + rate +
                '}';
    }
}
