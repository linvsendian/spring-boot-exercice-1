package com.exercice.persistence.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Currency persistence service.
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.exercice.currency.persistence.entity")
public class PersistenceCurrency {

  public static void main(String[] args) {
    SpringApplication.run(PersistenceCurrency.class, args);
  }
}
