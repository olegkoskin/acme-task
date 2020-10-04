package com.acme.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.acme.task")
public class AcmeTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcmeTaskApplication.class, args);
  }

}
