package com.exercice.business.currency.mapper;

import com.exercice.business.currency.model.CurrencyResponse;
import com.exercice.currency.persistence.entity.Currency;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Currency service mapper.
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface ICurrencyServiceMapper {

  CurrencyResponse entityToResponse(Currency currency);

  List<CurrencyResponse> entitiesToResponseList(List<Currency> currencyList);
}
