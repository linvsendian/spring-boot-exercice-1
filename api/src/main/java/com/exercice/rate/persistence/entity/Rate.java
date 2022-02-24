package com.exercice.rate.persistence.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Rate entity.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_rates")
@DynamicUpdate
public class Rate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "brand_id")
  private Integer brandId;

  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "start_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Column(name = "end_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name = "price")
  private Integer price;

  @Column(name = "currency_code")
  private String currencyCode;

}
