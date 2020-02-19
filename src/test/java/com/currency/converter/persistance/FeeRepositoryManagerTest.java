package com.currency.converter.persistance;

import static com.currency.converter.domain.Currency.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.CurrencyFeeEntity;
import com.currency.converter.domain.CurrencyInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FeeRepositoryManagerTest.MockConfig.class, FeeRepositoryManager.class})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class FeeRepositoryManagerTest {

    @Autowired
    private IFeeRepository iFeeRepository;
    @Autowired
    private FeeRepositoryManager feeRepositoryManager;

    @BeforeEach
    void setUp() {
        when(iFeeRepository.findAll()).thenReturn(getListOfEntities());
    }

    @Test
    void saveFee_happyPath() {
        feeRepositoryManager.saveFee(getCurrencyInfo(USD, EUR));
        verify(iFeeRepository, atMostOnce()).saveAndFlush(getCurrencyFeeEntity(USD, EUR));
    }

    @Test
    void getFee_happyPath() {
        final Optional<BigDecimal> fee = feeRepositoryManager.getFee(USD, THB);
        assertAll(() -> {
            assertTrue(fee.isPresent());
            assertEquals(new BigDecimal("0.05"), fee.get());
        });
    }

    @Test
    void getFee_noFee_returnsEmpty() {
        final Optional<BigDecimal> fee = feeRepositoryManager.getFee(USD, RUB);
        assertFalse(fee.isPresent());
    }

    @Test
    void getFees_returnListOfFees() {
        final List<CurrencyInfo> fees = feeRepositoryManager.getFees();
        assertAll(() -> {
            assertEquals(3, fees.size());
            assertEquals(USD, fees.get(2).getCurrencyFrom());
        });
    }

    @Test
    void deleteFee_happyPath() {
        feeRepositoryManager.deleteFee(getCurrencyInfo(EUR, USD));
        verify(iFeeRepository, atMostOnce()).delete(getCurrencyFeeEntity(EUR, USD));
    }

    @Test
    void deleteFee_noEntity() {
        feeRepositoryManager.deleteFee(getCurrencyInfo(EUR, RUB));
        verify(iFeeRepository, never()).delete(any(CurrencyFeeEntity.class));
    }

    private static List<CurrencyFeeEntity> getListOfEntities() {
        final List<CurrencyFeeEntity> list = new ArrayList<>();
        list.add(getCurrencyFeeEntity(EUR, USD));
        list.add(getCurrencyFeeEntity(EUR, THB));
        list.add(getCurrencyFeeEntity(USD, THB));
        return list;
    }

    private static CurrencyFeeEntity getCurrencyFeeEntity(final Currency currencyFrom, final Currency currencyTo) {
        final CurrencyFeeEntity currencyFeeEntity = new CurrencyFeeEntity();
        currencyFeeEntity.setCurrencyInfo(getCurrencyInfo(currencyFrom, currencyTo));
        return currencyFeeEntity;
    }

    private static CurrencyInfo getCurrencyInfo(final Currency currencyFrom, final Currency currencyTo) {
        final CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setCurrencyFrom(currencyFrom);
        currencyInfo.setCurrencyTo(currencyTo);
        currencyInfo.setFee(new BigDecimal("0.05"));
        return currencyInfo;
    }

    static class MockConfig {
        @Bean
        IFeeRepository iFeeRepository() {
            return mock(IFeeRepository.class);
        }
    }
}