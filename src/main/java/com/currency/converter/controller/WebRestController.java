package com.currency.converter.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;

import com.currency.converter.component.ICurrencyCalculationManager;
import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.CurrencyExchangeInfo;
import com.currency.converter.domain.CurrencyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebRestController {

    private final ICurrencyCalculationManager iCurrencyCalculationManager;
    private final IFeeManager iFeeManager;

    @Autowired
    public WebRestController(@NonNull final ICurrencyCalculationManager iCurrencyCalculationManager,
                             @NonNull final IFeeManager iFeeManager) {
        this.iCurrencyCalculationManager = iCurrencyCalculationManager;
        this.iFeeManager = iFeeManager;
    }

    @GetMapping(value = "/calculate", produces = APPLICATION_JSON_VALUE)
    public BigDecimal calculate(@Valid final CurrencyExchangeInfo currencyExchangeInfo) {
        return iCurrencyCalculationManager.exchangeCurrency(currencyExchangeInfo);
    }

    @PostMapping("/fee")
    public void saveFee(@RequestBody @Valid final CurrencyInfo currencyInfo) {
        iFeeManager.saveFee(currencyInfo);
    }

    @DeleteMapping("/fee")
    public void deleteFee(@Valid final CurrencyInfo currencyInfo) {
        iFeeManager.deleteFee(currencyInfo);
    }

    @GetMapping("/fees")
    public List<CurrencyInfo> getFees() {
        return iFeeManager.getAllFees();
    }
}
