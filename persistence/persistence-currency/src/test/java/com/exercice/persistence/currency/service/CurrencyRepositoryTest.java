package com.exercice.persistence.currency.service;

import static org.junit.jupiter.api.Assertions.*;

import com.exercice.currency.persistence.entity.Currency;
import com.exercice.persistence.currency.repository.ICurrencyRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Transactional
@Rollback
class CurrencyRepositoryTest {

  @Autowired
  ICurrencyRepository currencyRepository;

  @Test
  void getCurrencyByCode() {
    Optional<Currency> currency = currencyRepository.findCurrencyByCode("EUR");
    assertTrue(currency.isPresent());
    assertEquals("€", currency.get().getSymbol());
    assertEquals(2, currency.get().getDecimals());
  }

  @Test
  void getCurrencyList() {
    List<Currency> currencyList = currencyRepository.findAll();
    assertEquals(2, currencyList.size());
    List<Currency> currencyFilteredList = currencyList.stream()
        .filter(currency -> currency.getCode().equals("EUR") || currency.getCode().equals("USD"))
        .collect(
            Collectors.toList());
    assertEquals(currencyFilteredList.size(), currencyList.size());
  }
}