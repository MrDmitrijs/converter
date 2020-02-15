package com.currency.converter.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CURRENCY_RATES")
public class CurrencyFeeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private CurrencyInfo currencyInfo;

    public CurrencyFeeEntity() {
    }

    public CurrencyFeeEntity(final CurrencyInfo currencyInfo) {
        this.currencyInfo = currencyInfo;
    }

    public Long getId() {
        return id;
    }

    public CurrencyInfo getCurrencyInfo() {
        return currencyInfo;
    }

    public void setCurrencyInfo(CurrencyInfo currencyInfo) {
        this.currencyInfo = currencyInfo;
    }
}
