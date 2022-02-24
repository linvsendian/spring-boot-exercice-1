package com.exercice.persistence.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Rate persistence service.
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.exercice.rate.persistence.entity")
public class PersistenceRate {

  public static void main(String[] args) {
    SpringApplication.run(PersistenceRate.class, args);
  }
}
