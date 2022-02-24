package com.exercice.rate.persistence.service;

import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import java.util.List;

/**
 * Rate service interface.
 */
public interface IRateService extends IRatePersistence {

  boolean duplicateRate(int brandId, int productId, String currencyCode);

  List<Rate> getRatesByFilter(RateFilterDto rateFilterDto);
}
