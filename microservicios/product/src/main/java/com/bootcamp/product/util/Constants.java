package com.bootcamp.product.util;

public class Constants {
  /*
   * Routes
   * */
  public static final String BASE_URL = "http://localhost:3002";

  /*
   * Microservice Customer
   * */
  public static final String GET_CUSTOMER_DOCUMENT = "/customers/{document}";
  public static final String CUSTOMER_TYPE_PERSONAL = "PERSONAL";
  public static final String CUSTOMER_TYPE_EMPRESARIAL = "EMPRESARIAL";

  /*
   * Microservice Product->TCard
   * */
  public static final String POST_TRANSACTION_CARD = "/transactionCard";
  public static final String POST_TRANSACTION_CREDIT = "/transactionCredit";
}
