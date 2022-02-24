package com.exercice.persistence.rate.service;

import static org.junit.jupiter.api.Assertions.*;

import com.exercice.persistence.rate.repository.IRateRepository;
import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Transactional
@Rollback
class RateRepositoryTest {

  @Autowired
  IRateRepository rateRepository;

  @Test
  void getRateById() {
    Rate rate = Rate.builder().brandId(1).currencyCode("EUR").endDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    rateRepository.save(rate);
    Optional<Rate> optionalRate = rateRepository.findRateById(1);
    assertTrue(optionalRate.isPresent());
  }

  @Test
  void insertRate() {
    Rate rate = Rate.builder().brandId(1).currencyCode("EUR").endDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    Rate rateSaved = rateRepository.save(rate);
    assertEquals(rateSaved, rate);
  }

  @Test
  void updateRate() {
    Rate rate = Rate.builder().brandId(1).currencyCode("EUR").endDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    rateRepository.save(rate);
    rate.setBrandId(12);
    Rate rateUpdated = rateRepository.save(rate);
    assertEquals(rateUpdated, rate);
  }

  @Test
  void deleteRateById() {
    Rate rate = Rate.builder().brandId(1).currencyCode("EUR").endDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    Rate rateSaved = rateRepository.save(rate);
    rateRepository.deleteById(rateSaved.getId());
    assertFalse(rateRepository.findRateById(rateSaved.getId()).isPresent());
  }

  @Test
  void duplicateRate() {
    Rate rate = Rate.builder().brandId(199).productId(299).currencyCode("EUR")
        .endDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    rateRepository.save(rate);
    Optional<Rate> optionalRate = rateRepository.findRateByBrandIdAndProductIdAndCurrencyCode(
        rate.getBrandId(),
        rate.getProductId(), rate.getCurrencyCode());
    assertTrue(optionalRate.isPresent());
    assertEquals(optionalRate.get(), rate);
  }

  @Test
  void getRatesByFilter() {
    // Arrange
    Rate rate1 = Rate.builder().brandId(1).productId(1).currencyCode("EUR")
        .startDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    Rate rate2 = Rate.builder().brandId(1).productId(2).currencyCode("EUR")
        .startDate(LocalDate.now())
        .endDate(LocalDate.now()).price(10).build();
    Rate rate3 = Rate.builder().brandId(1).productId(1).currencyCode("EUR")
        .startDate(LocalDate.of(2022, 1, 1))
        .endDate(LocalDate.of(2022, 3, 1)).price(10).build();
    rateRepository.save(rate1);
    rateRepository.save(rate2);
    rateRepository.save(rate3);
    RateFilterDto rateFilterDto = RateFilterDto.builder().build();
    // Only brandId
    rateFilterDto.setBrandId(1);
    List<Rate> rateList = rateRepository.findRateByRateFilter(rateFilterDto);
    assertEquals(3, rateList.size());

    // brandId and productId
    rateFilterDto.setProductId(1);
    rateList = rateRepository.findRateByRateFilter(rateFilterDto);
    assertEquals(2, rateList.size());

    // brandId. productId and date
    rateFilterDto.setDate(LocalDate.now());
    System.out.println(LocalDate.now().toString());
    rateList = rateRepository.findRateByRateFilter(rateFilterDto);
    assertEquals(2, rateList.size());

  }
}