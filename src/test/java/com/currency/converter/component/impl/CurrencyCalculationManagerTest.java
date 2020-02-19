package com.currency.converter.component.impl;

import static com.currency.converter.domain.Currency.EUR;
import static com.currency.converter.domain.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.CurrencyExchangeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CurrencyCalculationManager.class, CurrencyCalculationManagerTest.MockConfig.class})
class CurrencyCalculationManagerTest {

    @Autowired
    private IFeeManager iFeeManager;
    @Autowired
    private RateRetriever rateRetriever;
    @Autowired
    private CurrencyCalculationManager currencyCalculationManager;

    @Test
    void exchangeCurrency_happyPath() {
        final CurrencyExchangeInfo currencyExchangeInfo = createTestObject();
        when(iFeeManager.getFee(currencyExchangeInfo.getCurrencyFrom(), currencyExchangeInfo.getCurrencyTo()))
                .thenReturn(new BigDecimal("0.05"));
        when(rateRetriever.getRate(currencyExchangeInfo.getCurrencyFrom(), currencyExchangeInfo.getCurrencyTo()))
                .thenReturn(new BigDecimal("1.10"));
        assertEquals(new BigDecimal("104.50"), currencyCalculationManager.exchangeCurrency(currencyExchangeInfo));
    }

    private CurrencyExchangeInfo createTestObject() {
        final CurrencyExchangeInfo currencyExchangeInfo = new CurrencyExchangeInfo();
        currencyExchangeInfo.setCurrencyTo(EUR);
        currencyExchangeInfo.setCurrencyFrom(USD);
        currencyExchangeInfo.setAmount(new BigDecimal("100"));
        return currencyExchangeInfo;
    }

    static class MockConfig {
        @Bean
        public IFeeManager iFeeManager() {
            return mock(IFeeManager.class);
        }

        @Bean
        public RateRetriever rateRetriever() {
            return mock(RateRetriever.class);
        }
    }
}