package com.currency.converter.component.impl;

import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.Currency;
import com.currency.converter.domain.CurrencyInfo;
import com.currency.converter.persistance.FeeRepositoryManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class FeeManager implements IFeeManager {

    private final BigDecimal defaultFee;
    private final FeeRepositoryManager feeRepositoryManager;

    public FeeManager(@NonNull final FeeRepositoryManager feeRepositoryManager,
            @NonNull @Value("${exchange.default.fee}") final String defaultFee) {
        this.defaultFee = new BigDecimal(defaultFee);
        this.feeRepositoryManager = feeRepositoryManager;
    }

    @Override
    public BigDecimal getFee(final Currency currencyFrom, final Currency currencyTo) {
        final Optional<BigDecimal> fee = feeRepositoryManager.getFee(currencyFrom, currencyTo);
        return fee.orElse(defaultFee);
    }

    @Override
    public void saveFee(final CurrencyInfo currencyInfo) {
        feeRepositoryManager.saveFee(currencyInfo);
    }

    @Override
    public void deleteFee(final CurrencyInfo currencyInfo) {
        feeRepositoryManager.deleteFee(currencyInfo);
    }

    @Override
    public List<CurrencyInfo> getAllFees() {
        return feeRepositoryManager.getFees();
    }
}
