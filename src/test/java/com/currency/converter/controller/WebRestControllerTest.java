package com.currency.converter.controller;

import static com.currency.converter.domain.Currency.*;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import com.currency.converter.component.ICurrencyCalculationManager;
import com.currency.converter.component.IFeeManager;
import com.currency.converter.domain.CurrencyExchangeInfo;
import com.currency.converter.domain.CurrencyInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WebRestControllerTest.MockConfig.class})
@AutoConfigureMockMvc
class WebRestControllerTest {

    private static final String CURRENCY_EXCHANGE_INFO = "?amount=100&currencyTo=THB&currencyFrom=EUR";
    private static final String CURRENCY_INFO_PARAM_REQUEST = "?fee=005&currencyTo=EUR&currencyFrom=USD";
    private static final String CURRENCY_INFO_BODY_REQUEST = "{\"fee\":0.05,\"currencyFrom\":\"USD\",\"currencyTo\":\"EUR\"}";
    private static final String POSITIVE_RESPONSE = "120";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICurrencyCalculationManager iCurrencyCalculationManager;

    @Autowired
    private IFeeManager iFeeManager;

    @Test
    void calculate() throws Exception {
        final CurrencyExchangeInfo currencyExchangeInfo = new CurrencyExchangeInfo();
        currencyExchangeInfo.setAmount(new BigDecimal("100"));
        currencyExchangeInfo.setCurrencyTo(THB);
        currencyExchangeInfo.setCurrencyFrom(EUR);
        when(iCurrencyCalculationManager.exchangeCurrency(currencyExchangeInfo)).thenReturn(new BigDecimal("120"));

        mockMvc.perform(get("/calculate" + CURRENCY_EXCHANGE_INFO).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(POSITIVE_RESPONSE));
        verify(iCurrencyCalculationManager, atMostOnce()).exchangeCurrency(currencyExchangeInfo);
    }

    @Test
    void saveFee() throws Exception {
        mockMvc.perform(post("/fee")
                .contentType(APPLICATION_JSON_VALUE)
                .content(CURRENCY_INFO_BODY_REQUEST))
                .andExpect(status().isOk());
        verify(iFeeManager, atMostOnce()).saveFee(getCurrencyInfo());
    }

    @Test
    void deleteFee() throws Exception {
        mockMvc.perform(delete("/fee" + CURRENCY_INFO_PARAM_REQUEST))
                .andExpect(status().isOk());
        verify(iFeeManager, atMostOnce()).deleteFee(getCurrencyInfo());
    }

    @Test
    void getFees() throws Exception {
        when(iFeeManager.getAllFees()).thenReturn(emptyList());
        mockMvc.perform(get("/fees"))
                .andExpect(status().isOk());
        verify(iFeeManager, atMostOnce()).getAllFees();
    }

    private static CurrencyInfo getCurrencyInfo() {
        final CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setFee(new BigDecimal("0.05"));
        currencyInfo.setCurrencyTo(EUR);
        currencyInfo.setCurrencyFrom(USD);
        return currencyInfo;
    }

    @TestConfiguration
    @EnableWebMvc
    static class MockConfig {
        @Bean
        ICurrencyCalculationManager iCurrencyCalculationManager() {
            return mock(ICurrencyCalculationManager.class);
        }

        @Bean
        IFeeManager iFeeManager() {
            return mock(IFeeManager.class);
        }
    }
}