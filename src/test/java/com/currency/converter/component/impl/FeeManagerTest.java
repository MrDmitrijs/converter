package com.currency.converter.component.impl;

import com.currency.converter.persistance.FeeRepositoryManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FeeManagerTest.MockConfig.class, FeeManager.class})
class FeeManagerTest {

    @Test
    void getFee() {
    }

    @Test
    void saveFee() {
    }

    @Test
    void deleteFee() {
    }

    @Test
    void getAllFees() {
    }


    static class MockConfig {
        @Bean
        public FeeRepositoryManager feeRepositoryManager() {
            return mock(FeeRepositoryManager.class);
        }
    }
}