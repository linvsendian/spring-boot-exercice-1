package com.exercice.business.currency.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.exercice.business.currency.model.CurrencyResponse;
import com.exercice.currency.persistence.entity.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ICurrencyServiceMapper.class, ICurrencyServiceMapperImpl.class})
class ICurrencyServiceMapperTest {

  @Autowired
  ICurrencyServiceMapper currencyServiceMapper;

  @Test
  void CurrencyEntityToResponse() {
    // Arrange
    String expectedCode = "CODE", expectedSymbol = "SYMBOL";
    int expectedDecimals = 3;
    Currency mockCurrency = Currency.builder().id(1).code(expectedCode).decimals(expectedDecimals)
        .symbol(expectedSymbol).build();
    // Act
    CurrencyResponse response = currencyServiceMapper.entityToResponse(mockCurrency);
    // Assert
    assertTrue(response.getCode().equals(expectedCode)
        && response.getDecimals() == expectedDecimals
        && response.getSymbol().equals(expectedSymbol));
  }

  @Test
  void CurrencyListToResponseList() {

    // Arrange
    String expectedCode = "CODE", expectedSymbol = "SYMBOL";
    int expectedDecimals = 3;
    Currency mockCurrency = Currency.builder().id(1).code(expectedCode).decimals(expectedDecimals)
        .symbol(expectedSymbol).build();
    Currency mockCurrency2 = Currency.builder().id(2).code(expectedCode).decimals(expectedDecimals)
        .symbol(expectedSymbol).build();

    List<Currency> currencyList = Stream.of(mockCurrency, mockCurrency2)
        .collect(Collectors.toList());
    // Act
    List<CurrencyResponse> responseList = currencyServiceMapper.entitiesToResponseList(
        currencyList);
    // Assert
    assertEquals(2, responseList.size());
    responseList.forEach(response -> {
      assertTrue(
          response.getCode().equals(expectedCode) && response.getSymbol().equals(expectedSymbol)
              && response.getDecimals() == expectedDecimals);
    });
  }

  @Test
  void mapNullValue() {
    assertNull(currencyServiceMapper.entitiesToResponseList(null));
    assertNull(currencyServiceMapper.entityToResponse(null));
  }
}