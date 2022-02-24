package com.exercice.business.currency.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercice.business.currency.mapper.ICurrencyServiceMapper;
import com.exercice.business.currency.model.CurrencyResponse;
import com.exercice.currency.persistence.entity.Currency;
import com.exercice.currency.persistence.service.ICurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@AutoConfigureMockMvc
@WebMvcTest
@ComponentScan("com.exercice.business.currency.mapper")
class CurrencyControllerTest {

  @Mock
  ICurrencyServiceMapper currencyServiceMapper;

  @Mock
  ICurrencyService currencyService;

  @InjectMocks
  CurrencyController currencyController;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
  }

  @Test
  void getCurrencies_ok() throws Exception {
    // Arrange
    List<Currency> mockCurrencyList = new ArrayList<>();
    mockCurrencyList.add(Currency.builder().build());

    List<CurrencyResponse> mockResponseList = new ArrayList<>();
    mockResponseList.add(new CurrencyResponse());
    JSONArray mockJsonResult = new JSONArray();
    mockJsonResult.addAll(mockResponseList);

    when(currencyService.getCurrencyList()).thenReturn(mockCurrencyList);
    when(currencyServiceMapper.entitiesToResponseList(mockCurrencyList)).thenReturn(
        mockResponseList);

    // Act and assert
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/currencies"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(mockJsonResult.toJSONString(), true))
        .andReturn();
    verify(currencyServiceMapper, times(1)).entitiesToResponseList(mockCurrencyList);
    verify(currencyService, times(1)).getCurrencyList();
  }

  @Test
  void getCurrencyByCode_ok() throws Exception {
    // Arrange
    String codeToRequest = "EUR";
    Currency mockCurrency = Currency.builder().build();

    CurrencyResponse mockResponse = new CurrencyResponse();

    // Convert response to json object
    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject mockJsonResult = objectMapper.convertValue(mockResponse, JSONObject.class);

    when(currencyService.getCurrencyByCode(codeToRequest)).thenReturn(Optional.of(mockCurrency));
    when(currencyServiceMapper.entityToResponse(mockCurrency)).thenReturn(
        mockResponse);

    // Act and assert
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/currencies/" + codeToRequest))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .json(mockJsonResult.toJSONString(), true))
        .andReturn();
    verify(currencyServiceMapper, times(1)).entityToResponse(mockCurrency);
    verify(currencyService, times(1)).getCurrencyByCode(codeToRequest);
  }

  @Test
  void getCurrencyByCode_null() throws Exception {
    // Arrange
    String codeToRequest = "EUR";
    Optional<Currency> mockCurrency = Optional.empty();
    CurrencyResponse mockResponse = new CurrencyResponse();

    // Convert response to json object
    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject mockJsonResult = objectMapper.convertValue(mockResponse, JSONObject.class);

    when(currencyService.getCurrencyByCode(codeToRequest)).thenReturn(mockCurrency);

    // Act and assert
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/currencies/" + codeToRequest))
        .andExpect(status().isNotFound())
        .andExpect(
            mvcResult -> assertEquals("Currency not found",
                Objects.requireNonNull((ResponseStatusException) mvcResult.getResolvedException())
                    .getReason())
        )
        .andReturn();
    verify(currencyServiceMapper, times(0)).entityToResponse(null);
    verify(currencyService, times(1)).getCurrencyByCode(codeToRequest);

  }
}