package com.currency.converter.persistance;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.CurrencyFeeEntity;
import com.currency.converter.domain.CurrencyInfo;
import com.currency.converter.exception.FeeRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FeeRepositoryManager {
    private static final Logger LOG = LoggerFactory.getLogger(FeeRepositoryManager.class);
    private final IFeeRepository repository;

    @Autowired
    public FeeRepositoryManager(final IFeeRepository repository) {
        this.repository = repository;
    }

    public void saveFee(final CurrencyInfo currencyInfo) {
        final Optional<CurrencyFeeEntity> answer = findByCurrency(currencyInfo.getCurrencyFrom(), currencyInfo.getCurrencyTo());
        if (answer.isPresent()) {
            LOG.info("This currencyInfo is already exists: {}, delete it to update.", currencyInfo);
        } else {
            final CurrencyFeeEntity currencyFeeEntity = new CurrencyFeeEntity(currencyInfo);
            LOG.info("Going to save currency info: {}", currencyInfo);
            saveCurrencyFeeEntity(currencyFeeEntity);
        }
    }

    public Optional<BigDecimal> getFee(final Currency currencyFrom, final Currency currencyTo) {
        final Optional<CurrencyFeeEntity> answer = findByCurrency(currencyFrom, currencyTo);
        if(answer.isPresent()) {
            final CurrencyInfo currencyInfo = answer.get().getCurrencyInfo();
            final BigDecimal fee = currencyInfo.getFee();
            LOG.info("Found fee: {} , for currencyFrom: {} and currencyTo: {}", fee, currencyFrom, currencyTo);
            return Optional.of(fee);
        } else {
            LOG.error("Did not find fee for currencyFrom: {} and currencyTo: {}", currencyFrom, currencyTo);
            return Optional.empty();
        }
    }

    public void deleteFee(final CurrencyInfo currencyInfo) {

        final Optional<CurrencyFeeEntity> answer = getAllFess().stream()
                .filter(currencyFeeEntity -> currencyFeeEntity.getCurrencyInfo().equals(currencyInfo)).findFirst();

        if(answer.isPresent()) {
            final CurrencyFeeEntity currencyFeeEntity = answer.get();
            LOG.info("Going to delete currency info: {}", currencyFeeEntity.getCurrencyInfo());
            try {
                repository.delete(currencyFeeEntity);
            } catch (final Exception e) {
                LOG.error("Could not to delete entity: {}", currencyFeeEntity);
                throw new FeeRepositoryException("Failed to delete entity", e);
            }
        } else {
            LOG.error("Did not find currency info {} in data base", currencyInfo);
        }
    }

    public List<CurrencyInfo> getFees() {
        return getAllFess().stream()
                .map(CurrencyFeeEntity::getCurrencyInfo)
                .collect(Collectors.toList());
    }

    private Optional<CurrencyFeeEntity> findByCurrency(final Currency currencyFrom, final Currency currencyTo) {
        return getAllFess().stream().filter(currencyFeeEntity -> {
            final CurrencyInfo currencyInfo = currencyFeeEntity.getCurrencyInfo();
            return currencyInfo.getCurrencyFrom().equals(currencyFrom) && currencyInfo.getCurrencyTo().equals(currencyTo);
        }).findAny();
    }

    private void saveCurrencyFeeEntity(final CurrencyFeeEntity currencyFeeEntity) {
        try {
            repository.saveAndFlush(currencyFeeEntity);
        } catch (final Exception e) {
            LOG.error("Failed to store request entity: {}, with exception: {}", currencyFeeEntity, e);
            throw new FeeRepositoryException("Failed to store request entity.", e);
        }
    }

    private List<CurrencyFeeEntity> getAllFess() {
        final List<CurrencyFeeEntity> listOfFees;
        try {
            listOfFees = repository.findAll();
        } catch (final Exception e) {
            throw new FeeRepositoryException("Failed to get all fees.", e);
        }
        return listOfFees;
    }
}
