package com.exercice.business.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Business currency service.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BusinessCurrency {

  public static void main(String[] args) {
    SpringApplication.run(BusinessCurrency.class, args);
  }
}
