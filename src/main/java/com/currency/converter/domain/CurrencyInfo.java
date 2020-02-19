package com.currency.converter.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyInfo that = (CurrencyInfo) o;
        return Objects.equals(fee, that.fee) &&
                currencyFrom == that.currencyFrom &&
                currencyTo == that.currencyTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fee, currencyFrom, currencyTo);
    }

    @Override
    public String toString() {
        return "CurrencyInfo{" +
                "fee=" + fee +
                ", currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                '}';
    }
}
