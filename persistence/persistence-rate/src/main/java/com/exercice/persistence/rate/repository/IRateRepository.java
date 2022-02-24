package com.exercice.persistence.rate.repository;

import com.exercice.rate.dto.RateFilterDto;
import com.exercice.rate.persistence.entity.Rate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Rate repository.
 */
@Repository
public interface IRateRepository extends JpaRepository<Rate, Integer> {

  Optional<Rate> findRateById(int id);

  Optional<Rate> findRateByBrandIdAndProductIdAndCurrencyCode(int brandId, int productId,
      String currencyCode);

  @Query(value =
      "Select t.id,t.brand_id,t.product_id,t.start_date,t.end_date,t.price,t.currency_code "
          + "from t_rates t where "
          + "(coalesce(:#{#rateFilterDto.getDate()}, null) is null or t.start_date<=cast(cast(:#{#rateFilterDto.getDate()} as text)as date) ) "
          + "and (coalesce(:#{#rateFilterDto.getDate()}, null) is null or t.end_date>=cast(cast(:#{#rateFilterDto.getDate()} as text)as date)) "
          + "and (coalesce(:#{#rateFilterDto.getProductId()},null) is null or t.product_id=cast(cast(:#{#rateFilterDto.getProductId()} as text) as integer)) "
          + "and (coalesce(:#{#rateFilterDto.getBrandId()},null) is null or t.brand_id=cast(cast(:#{#rateFilterDto.getBrandId()} as text) as integer)) ",
      nativeQuery = true)
  List<Rate> findRateByRateFilter(@Param("rateFilterDto") RateFilterDto rateFilterDto);

}
