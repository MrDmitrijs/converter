package com.currency.converter.component;

import com.currency.converter.domain.CurrencyExchangeInfo;

import java.math.BigDecimal;

public interface ICurrencyCalculationManager {
    BigDecimal exchangeCurrency(final CurrencyExchangeInfo currencyExchangeInfo);
}
