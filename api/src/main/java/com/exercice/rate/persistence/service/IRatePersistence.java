package com.exercice.rate.persistence.service;

import com.exercice.rate.persistence.entity.Rate;
import java.util.Optional;

/**
 * Rate persistence service.
 */
public interface IRatePersistence {

  Optional<Rate> getRateById(int id);

  void insertRate(Rate rate);

  void updateRate(Rate rate);

  void deleteRateById(int id);
}
