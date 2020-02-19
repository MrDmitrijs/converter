package com.currency.converter.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class CurrencyExchangeInfo extends CurrencyInfo {

    @NotNull
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrencyExchangeInfo that = (CurrencyExchangeInfo) o;
        return Objects.equals(amount, that.amount) &&
                super.getCurrencyFrom().equals(that.getCurrencyFrom()) &&
                super.getCurrencyTo().equals(that.getCurrencyTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, super.getCurrencyTo(), super.getCurrencyFrom());
    }
}
