package com.bootcamp.product.client.customer;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class Customer {
  private String id;
  private String name;
  private String document;
  private String type;
  private LocalDateTime registrationDate;
}
