package com.exercice.business.currency.controller;

import com.exercice.business.currency.api.V1Api;
import com.exercice.business.currency.mapper.ICurrencyServiceMapper;
import com.exercice.business.currency.model.CurrencyResponse;
import com.exercice.currency.persistence.entity.Currency;
import com.exercice.currency.persistence.service.ICurrencyService;
import java.util.List;
import java.util.Optional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Currency controller.
 */
@RestController
public class CurrencyController implements V1Api {

  @DubboReference(version = "1.0.0")
  ICurrencyService currencyService;

  @Autowired
  ICurrencyServiceMapper currencyServiceMapper;

  @Override
  public ResponseEntity<List<CurrencyResponse>> getCurrencies() {
    List<Currency> currencyList = currencyService.getCurrencyList();
    List<CurrencyResponse> responseList = currencyServiceMapper.entitiesToResponseList(
        currencyList);
    return ResponseEntity.ok().body(responseList);
  }

  @Override
  public ResponseEntity<CurrencyResponse> getCurrencyByCode(String currencyCode) {
    Optional<Currency> currency = currencyService.getCurrencyByCode(currencyCode);
    CurrencyResponse response = currencyServiceMapper.entityToResponse(
        currency.orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Currency not found")));
    return ResponseEntity.ok().body(response);
  }
}
