package com.currency.converter.component.impl;

import com.currency.converter.component.ICurrencyCalculationManager;
import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.Currency;
import com.currency.converter.domain.CurrencyExchangeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyCalculationManager implements ICurrencyCalculationManager {

    private final IFeeManager iFeeManager;
    private final RateRetriever rateRetriever;

    @Autowired
    public CurrencyCalculationManager(@NonNull final IFeeManager iFeeManager,
                                      @NonNull final RateRetriever rateRetriever) {
        this.iFeeManager = iFeeManager;
        this.rateRetriever = rateRetriever;
    }

    @Override
    public BigDecimal exchangeCurrency(final CurrencyExchangeInfo currencyExchangeInfo) {
        final Currency currencyFrom = currencyExchangeInfo.getCurrencyFrom();
        final Currency currencyTo = currencyExchangeInfo.getCurrencyTo();

        final BigDecimal fee = iFeeManager.getFee(currencyFrom, currencyTo);
        final BigDecimal rate = rateRetriever.getRate(currencyFrom, currencyTo);
        final BigDecimal amount = currencyExchangeInfo.getAmount();

        return calculate(fee, rate, amount);
    }

    private BigDecimal calculate(final BigDecimal fee, final BigDecimal rate, final BigDecimal amount) {
        final BigDecimal amountWithFee = amount.multiply(fee);
        final BigDecimal amountWithFeeSubAmount = amount.subtract(amountWithFee);
        final BigDecimal multiplyWithRate = amountWithFeeSubAmount.multiply(rate);
        return multiplyWithRate.setScale(2, RoundingMode.CEILING);
    }


}
