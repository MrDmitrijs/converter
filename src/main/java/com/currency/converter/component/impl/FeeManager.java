package com.currency.converter.component.impl;

import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FeeManager implements IFeeManager {

    private final BigDecimal defaultFee;

    public FeeManager(@Value("${exchange.default.fee}") BigDecimal defaultFee) {
        this.defaultFee = defaultFee;
    }

    @Override
    public BigDecimal getFee(final Currency currencyFrom, final Currency currencyTo) {
        return defaultFee;
    }
}
