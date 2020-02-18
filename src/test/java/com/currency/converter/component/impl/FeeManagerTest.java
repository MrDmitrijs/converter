package com.currency.converter.component.impl;

import com.currency.converter.domain.CurrencyInfo;
import com.currency.converter.persistance.FeeRepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.currency.converter.domain.Currency.EUR;
import static com.currency.converter.domain.Currency.USD;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FeeManagerTest {

    private FeeManager feeManager;
    private FeeRepositoryManager feeRepositoryManager;

    @BeforeEach
    void setUp() {
        feeRepositoryManager = mock(FeeRepositoryManager.class);
        feeManager = new FeeManager(feeRepositoryManager, new BigDecimal("0.05"));
    }

    @Test
    void saveFee() {
        final CurrencyInfo currencyInfo = new CurrencyInfo();
        feeManager.saveFee(currencyInfo);
        verify(feeRepositoryManager, atLeastOnce()).saveFee(currencyInfo);
    }

    @Test
    void deleteFee() {
        final CurrencyInfo currencyInfo = new CurrencyInfo();
        feeManager.deleteFee(currencyInfo);
        verify(feeRepositoryManager, atLeastOnce()).deleteFee(currencyInfo);
    }

    @Test
    void getAllFees() {
        feeManager.getAllFees();
        verify(feeRepositoryManager, atLeastOnce()).getFees();
    }

    @Nested
    @DisplayName("Testing get fee")
    class test_getFee {
        private FeeManager feeManager;
        private FeeRepositoryManager feeRepositoryManager;

        @Test
        void getFee_getDefaultValue() {
            feeRepositoryManager = mock(FeeRepositoryManager.class);
            when(feeRepositoryManager.getFee(EUR, USD)).thenReturn(empty());
            feeManager = new FeeManager(feeRepositoryManager, new BigDecimal("0.05"));
            assertEquals(new BigDecimal("0.05"), feeManager.getFee(EUR, USD));
        }

        @Test
        void getFee_getNonDefaultValue() {
            feeRepositoryManager = mock(FeeRepositoryManager.class);
            when(feeRepositoryManager.getFee(EUR, USD)).thenReturn(Optional.of(new BigDecimal("0.10")));
            feeManager = new FeeManager(feeRepositoryManager, new BigDecimal("0.05"));
            assertEquals(new BigDecimal("0.10"), feeManager.getFee(EUR, USD));
        }
    }
}