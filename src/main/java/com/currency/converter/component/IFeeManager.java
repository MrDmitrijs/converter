package com.currency.converter.component;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.CurrencyInfo;

import java.math.BigDecimal;
import java.util.List;

public interface IFeeManager {
    BigDecimal getFee(final Currency currencyFrom, final Currency currencyTo);

    void saveFee(final CurrencyInfo currencyInfo);

    void deleteFee(final CurrencyInfo currencyInfo);

    List<CurrencyInfo> getAllFees();
}
