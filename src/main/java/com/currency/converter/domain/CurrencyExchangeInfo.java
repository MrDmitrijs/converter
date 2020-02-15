package com.currency.converter.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CurrencyExchangeInfo extends CurrencyInfo {

    @NotNull
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
