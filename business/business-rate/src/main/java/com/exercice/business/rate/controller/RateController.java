package com.exercice.business.rate.controller;

import com.exercice.business.rate.api.V1Api;
import com.exercice.business.rate.mapper.IRateServiceMapper;
import com.exercice.business.rate.model.RateFilter;
import com.exercice.business.rate.model.RateModel;
import com.exercice.business.rate.model.RateResponse;
import com.exercice.business.rate.model.RateUpdateModel;
import com.exercice.currency.persistence.entity.Currency;
import com.exercice.currency.persistence.service.ICurrencyService;
import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import com.exercice.rate.persistence.service.IRateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rate controller.
 */
@RestController
public class RateController implements V1Api {

  @Autowired
  IRateServiceMapper rateServiceMapper;

  @DubboReference(version = "1.0.0")
  private IRateService rateService;

  @DubboReference(version = "1.0.0")
  private ICurrencyService currencyService;

  @Override
  public ResponseEntity<Void> deleteRateById(Integer id) {
    Optional<Rate> optionalRate = rateService.getRateById(id);
    if (!optionalRate.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rate not exists");
    }
    rateService.deleteRateById(id);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<RateResponse> getRateById(Integer rateId) {
    // Get rate
    Optional<Rate> optionalRate = rateService.getRateById(rateId);
    Rate rate = optionalRate.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate not found"));

    // Get Currency
    Optional<Currency> optionalCurrency = currencyService.getCurrencyByCode(
        rate.getCurrencyCode());
    Currency currency = optionalCurrency.orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Currency not found"));

    RateResponse response = rateServiceMapper.rateCurrencyToResponse(rate, currency, currency);

    return ResponseEntity.ok().body(response);
  }

  @Override
  public ResponseEntity<Void> insertRate(RateModel rate) {
    Optional<Currency> optionalCurrency = currencyService.getCurrencyByCode(
        rate.getCurrencyCode());
    if (!optionalCurrency.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not found");
    }
    Rate newRate = rateServiceMapper.rateModelToEntity(rate);
    if (rateService.duplicateRate(newRate.getBrandId(), newRate.getProductId(),
        newRate.getCurrencyCode())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "There is a rate with the same brand ID, product ID and currency code");
    }
    rateService.insertRate(newRate);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<List<RateResponse>> searchRateByFilter(RateFilter rateFilter) {

    RateFilterDto rateFilterDto = rateServiceMapper.rateFilterToDto(rateFilter);
    List<Currency> currencyList = currencyService.getCurrencyList();
    List<Rate> rateList = rateService.getRatesByFilter(rateFilterDto);
    List<RateResponse> rateResponseList = new ArrayList<>();
    for (Rate rate : rateList) {
      for (Currency currency : currencyList) {
        if (rate.getCurrencyCode().equals(currency.getCode())) {
          RateResponse response = rateServiceMapper.rateCurrencyToResponse(rate, currency,
              currency);
          rateResponseList.add(response);
        }
      }
    }
    return ResponseEntity.ok().body(rateResponseList);
  }

  @Override
  public ResponseEntity<Void> updateRate(RateUpdateModel rateUpdateModel) {
    Optional<Rate> optionalRate = rateService.getRateById(rateUpdateModel.getId());
    if (!optionalRate.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rate not exists");
    }
    Rate rateToUpdate = rateServiceMapper.mergeRateUpdateModelAndExistRateToEntity(
        optionalRate.get(), rateUpdateModel);
    rateService.updateRate(rateToUpdate);
    return ResponseEntity.ok().build();
  }
}
