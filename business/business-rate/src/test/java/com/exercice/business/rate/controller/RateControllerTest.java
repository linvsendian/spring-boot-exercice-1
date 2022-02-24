package com.exercice.business.rate.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercice.business.rate.mapper.IRateServiceMapper;
import com.exercice.business.rate.model.RateModel;
import com.exercice.business.rate.model.RateResponse;
import com.exercice.currency.persistence.entity.Currency;
import com.exercice.currency.persistence.service.ICurrencyService;
import com.exercice.rate.persistence.entity.Rate;
import com.exercice.rate.persistence.service.IRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Optional;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@WebMvcTest
@ComponentScan("com.exercice.business.rate.mapper")
class RateControllerTest {

  @Mock
  IRateServiceMapper rateServiceMapper;

  @Mock
  IRateService rateService;

  @Mock
  ICurrencyService currencyService;

  @InjectMocks
  RateController rateController;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
  }

  @Test
  void deleteRateById() throws Exception {
    int idToRequest = 1;
    RateResponse mockResponse = new RateResponse();
    ObjectMapper objectMapper = new ObjectMapper();

    when(rateService.getRateById(1)).thenReturn(Optional.of(Rate.builder().build()));

    // Act
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/rate/delete").param("id", "1"))
        .andExpect(status().isOk())
        .andReturn();

    verify(rateService, times(1)).deleteRateById(idToRequest);
    verify(rateService, times(1)).getRateById(1);
  }

  @Test
  void getRateById() throws Exception {// Arrange
    int idToRequest = 1;
    Optional<Rate> optionalRate = Optional.of(Rate.builder().build());
    Optional<Currency> optionalCurrency = Optional.of(Currency.builder().build());
    RateResponse mockResponse = new RateResponse();
    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject mockJsonResult = objectMapper.convertValue(mockResponse, JSONObject.class);

    when(rateService.getRateById(idToRequest)).thenReturn(optionalRate);
    when(currencyService.getCurrencyByCode(any())).thenReturn(optionalCurrency);
    when(rateServiceMapper.rateCurrencyToResponse(optionalRate.get(), optionalCurrency.get(),
        optionalCurrency.get())).thenReturn(mockResponse);

    // Act
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/rate/" + idToRequest))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .json(mockJsonResult.toJSONString(), true))
        .andReturn();
    verify(rateServiceMapper, times(1)).rateCurrencyToResponse(optionalRate.get(),
        optionalCurrency.get(),
        optionalCurrency.get());
    verify(rateService, times(1)).getRateById(idToRequest);
  }

  @Test
  void insertRate() throws Exception {
    RateModel model = new RateModel();
    model.setBrandId(1);
    model.setCurrencyCode("EUR");
    model.setProductId(1);
    model.setPrice(new BigDecimal(1));
    Rate newRate = Rate.builder().productId(1).price(1).currencyCode("EUR").brandId(1).build();
    Optional<Rate> optionalRate = Optional.of(newRate);
    Optional<Currency> optionalCurrency = Optional.of(Currency.builder().build());
    RateResponse mockResponse = new RateResponse();
    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject mockJsonRequest = objectMapper.convertValue(model, JSONObject.class);

    mockJsonRequest.put("startDate", "2022-02-22");
    mockJsonRequest.put("endDate", "2022-02-22");
    when(
        rateService.duplicateRate(newRate.getBrandId(), newRate.getProductId(),
            newRate.getCurrencyCode()))
        .thenReturn(false);
    when(currencyService.getCurrencyByCode(any())).thenReturn(optionalCurrency);
    when(rateServiceMapper.rateModelToEntity(any())).thenReturn(newRate);

    // Act
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/rate/insert")
                .content(mockJsonRequest.toJSONString()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    verify(rateService, times(1)).duplicateRate(newRate.getBrandId(), newRate.getProductId(),
        newRate.getCurrencyCode());
    verify(currencyService, times(1)).getCurrencyByCode(any());
    verify(rateServiceMapper, times(1)).rateModelToEntity(any());
  }

  @Test
  void searchRateByFilter() {
    // TODO
  }

  @Test
  void updateRate() {
    // TODO
  }
}