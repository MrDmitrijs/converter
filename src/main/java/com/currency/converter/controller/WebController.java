package com.currency.converter.controller;

import com.currency.converter.component.ICurrencyCalculationManager;
import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.CurrencyExchangeInfo;
import com.currency.converter.domain.CurrencyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class WebController {

    private final ICurrencyCalculationManager iCurrencyCalculationManager;
    private final IFeeManager iFeeManager;

    @Autowired
    public WebController(@NonNull final ICurrencyCalculationManager iCurrencyCalculationManager,
                         @NonNull final IFeeManager iFeeManager) {
        this.iCurrencyCalculationManager = iCurrencyCalculationManager;
        this.iFeeManager = iFeeManager;
    }

    @GetMapping("/calculate")
    public BigDecimal calculate(@Valid final CurrencyExchangeInfo currencyExchangeInfo) {
        return iCurrencyCalculationManager.exchangeCurrency(currencyExchangeInfo);
    }

    //TODO: change to post
    @GetMapping("/save/fee")
    public void saveFee(@Valid final CurrencyInfo currencyInfo) {
        iFeeManager.saveFee(currencyInfo);
    }

    //TODO: change to delete
    @GetMapping("/delete/fee")
    public void deleteFee(@Valid final CurrencyInfo currencyInfo) {
        iFeeManager.deleteFee(currencyInfo);
    }

    @GetMapping("/fees")
    public List<CurrencyInfo> getFees() {
        return iFeeManager.getAllFees();
    }


}
