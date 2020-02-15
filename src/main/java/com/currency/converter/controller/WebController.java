package com.currency.converter.controller;

import com.currency.converter.component.ICurrencyCalculationManager;
import com.currency.converter.domain.CurrencyExchangeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class WebController {

    private final ICurrencyCalculationManager iCurrencyCalculationManager;

    @Autowired
    public WebController(ICurrencyCalculationManager iCurrencyCalculationManager) {
        this.iCurrencyCalculationManager = iCurrencyCalculationManager;
    }

    @GetMapping("/calculate")
    public BigDecimal calculate(@Valid final CurrencyExchangeInfo currencyExchangeInfo) {
        return iCurrencyCalculationManager.exchangeCurrency(currencyExchangeInfo);
    }
}
