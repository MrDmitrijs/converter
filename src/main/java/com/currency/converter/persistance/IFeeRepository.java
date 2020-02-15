package com.currency.converter.persistance;

import com.currency.converter.domain.CurrencyFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeeRepository extends JpaRepository<CurrencyFeeEntity, Long> {
}
