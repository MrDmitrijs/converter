package com.currency.converter.component;

import com.currency.converter.domain.Currency;

import java.math.BigDecimal;

public interface IFeeManager {
    BigDecimal getFee(final Currency currencyFrom, final Currency currencyTo);
}
