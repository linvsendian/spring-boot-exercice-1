package com.exercice.currency.persistence.service;

import com.exercice.currency.persistence.entity.Currency;
import java.util.List;
import java.util.Optional;

public interface ICurrencyService {

  Optional<Currency> getCurrencyByCode(String code);

  List<Currency> getCurrencyList();
}
