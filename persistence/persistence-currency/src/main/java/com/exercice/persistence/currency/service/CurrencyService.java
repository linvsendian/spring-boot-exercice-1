package com.exercice.persistence.currency.service;

import com.exercice.currency.persistence.entity.Currency;
import com.exercice.currency.persistence.service.ICurrencyService;
import com.exercice.persistence.currency.repository.ICurrencyRepository;
import java.util.List;
import java.util.Optional;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Currency service.
 */
@DubboService(version = "1.0.0")
public class CurrencyService implements ICurrencyService {

  @Autowired
  ICurrencyRepository currencyRepository;

  @Override
  public Optional<Currency> getCurrencyByCode(String code) {
    return currencyRepository.findCurrencyByCode(code);
  }

  @Override
  public List<Currency> getCurrencyList() {
    return currencyRepository.findAll();
  }
}
