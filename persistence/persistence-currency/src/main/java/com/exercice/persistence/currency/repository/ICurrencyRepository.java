package com.exercice.persistence.currency.repository;

import com.exercice.currency.persistence.entity.Currency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Currency repository.
 */
@Repository
public interface ICurrencyRepository extends JpaRepository<Currency, Integer> {

  Optional<Currency> findCurrencyByCode(String code);

}
