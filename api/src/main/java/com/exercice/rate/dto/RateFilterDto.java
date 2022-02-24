package com.exercice.rate.dto;

import java.time.LocalDate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rate filter for searching.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateFilterDto {

  @Temporal(TemporalType.DATE)
  private LocalDate date;
  private Integer productId;
  private Integer brandId;

}
