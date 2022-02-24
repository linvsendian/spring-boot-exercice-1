package com.exercice.persistence.currency.service;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.exercice.currency.persistence.entity.Currency;
import com.exercice.persistence.currency.repository.ICurrencyRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

  @Mock
  ICurrencyRepository currencyRepository;

  @InjectMocks
  CurrencyService currencyService;

  @Test
  void getCurrencyByCode() {
    // Arrange
    when(currencyRepository.findCurrencyByCode(anyString())).thenReturn(
        Optional.of(Currency.builder().build()));
    // Act
    currencyService.getCurrencyByCode("USD");
    // Assert
    verify(currencyRepository, times(1)).findCurrencyByCode("USD");
  }

  @Test
  void getCurrencyList() {
    // Arrange
    when(currencyRepository.findAll()).thenReturn(Stream.of(Currency.builder().build()).collect(
        Collectors.toList()));
    // Act
    currencyService.getCurrencyList();
    // Assert
    verify(currencyRepository, times(1)).findAll();
  }
}