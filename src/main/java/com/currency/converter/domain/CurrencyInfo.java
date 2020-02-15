package com.currency.converter.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CurrencyInfo {

    private BigDecimal fee;
    @NotNull
    private Currency currencyFrom;
    @NotNull
    private Currency currencyTo;

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }

}
