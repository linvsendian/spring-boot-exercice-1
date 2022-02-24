package com.exercice.persistence.rate.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.exercice.persistence.rate.repository.IRateRepository;
import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

  @Mock
  IRateRepository rateRepository;

  @InjectMocks
  RateService rateService;

  @Test
  void getRateById() {
    when(rateRepository.findRateById(1)).thenReturn(Optional.of(Rate.builder().build()));
    rateService.getRateById(1);
    verify(rateRepository, times(1)).findRateById(1);
  }

  @Test
  void insertRate() {
    when(rateRepository.save(any())).then(invocationOnMock -> Rate.builder().build());
    rateService.insertRate(Rate.builder().build());
    verify(rateRepository, times(1)).save(any());

  }

  @Test
  void updateRate() {
    when(rateRepository.save(any())).then(invocationOnMock -> Rate.builder().build());
    rateService.updateRate(Rate.builder().build());
    verify(rateRepository, times(1)).save(any());
  }

  @Test
  void deleteRateById() {
    rateService.deleteRateById(1);
    verify(rateRepository, times(1)).deleteById(1);
  }

  @Test
  void duplicateRate() {
    when(rateRepository.findRateByBrandIdAndProductIdAndCurrencyCode(1, 1, "EUR")).thenReturn(
        Optional.of(Rate.builder().build()));
    rateService.duplicateRate(1, 1, "EUR");
    verify(rateRepository, times(1)).findRateByBrandIdAndProductIdAndCurrencyCode(1, 1, "EUR");
  }

  @Test
  void getRatesByFilter() {
    when(rateRepository.findRateByRateFilter(any())).thenReturn(
        Stream.of(Rate.builder().build()).collect(Collectors.toList()));
    rateService.getRatesByFilter(any());
    verify(rateRepository, times(1)).findRateByRateFilter(any());
  }
}