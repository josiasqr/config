package com.bootcamp.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@EnableEurekaClient
@SpringBootApplication
@EnableKafka
public class TransactionApplication {
  public static void main(String[] args) {
    SpringApplication.run(TransactionApplication.class, args);
  }
}
