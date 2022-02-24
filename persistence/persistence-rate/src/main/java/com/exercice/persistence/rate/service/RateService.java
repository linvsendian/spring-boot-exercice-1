package com.exercice.persistence.rate.service;

import com.exercice.persistence.rate.repository.IRateRepository;
import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import com.exercice.rate.persistence.service.IRateService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Rate service.
 */
@Slf4j
@DubboService(version = "1.0.0")
public class RateService implements IRateService {

  @Autowired
  IRateRepository rateRepository;

  @Override
  public Optional<Rate> getRateById(int id) {
    return rateRepository.findRateById(id);
  }

  @Override
  public void insertRate(Rate rate) {
    rateRepository.save(rate);
  }

  @Override
  public void updateRate(Rate rate) {
    rateRepository.save(rate);
  }

  @Override
  public void deleteRateById(int id) {
    rateRepository.deleteById(id);
  }


  @Override
  public boolean duplicateRate(int brandId, int productId, String currencyCode) {
    return rateRepository.findRateByBrandIdAndProductIdAndCurrencyCode(brandId, productId,
        currencyCode).isPresent();
  }

  @Override
  public List<Rate> getRatesByFilter(RateFilterDto rateFilterDto) {
    return rateRepository.findRateByRateFilter(rateFilterDto);
  }
}
