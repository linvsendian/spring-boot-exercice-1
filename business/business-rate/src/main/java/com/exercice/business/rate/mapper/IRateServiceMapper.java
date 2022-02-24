package com.exercice.business.rate.mapper;

import com.exercice.business.rate.model.RateFilter;
import com.exercice.business.rate.model.RateModel;
import com.exercice.business.rate.model.RateResponse;
import com.exercice.business.rate.model.RateUpdateModel;
import com.exercice.currency.persistence.entity.Currency;
import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Rate service mapper.
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface IRateServiceMapper {

  // Convert LocalDateTime ot OffsetDateTime
  default OffsetDateTime map(LocalDateTime value) {
    return value.atOffset(ZoneOffset.UTC);
  }


  /**
   * Map a formatted price with float and symbol.
   *
   * @param value           Integer
   * @param currencyContext Currency
   * @return String
   */
  default String mapPriceFormatted(Integer value, @Context Currency currencyContext) {

    return BigDecimal.valueOf(value).setScale(currencyContext.getDecimals(), RoundingMode.HALF_EVEN)
        .toString().concat(currencyContext.getSymbol());
  }

  // Map currency symbol and id to response
  @Mapping(source = "currency.symbol", target = "currencySymbol")
  @Mapping(source = "rate.id", target = "id")
  @Mapping(source = "rate.price", target = "priceFormatted")
  RateResponse rateCurrencyToResponse(Rate rate, Currency currency,
      @Context Currency currencyContext);

  @Mapping(source = "rateUpdateModel.price", target = "price")
  @Mapping(source = "existRate.id", target = "id")
  Rate mergeRateUpdateModelAndExistRateToEntity(Rate existRate, RateUpdateModel rateUpdateModel);

  Rate rateModelToEntity(RateModel rateModel);

  RateFilterDto rateFilterToDto(RateFilter rateFilter);

}
