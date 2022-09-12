package com.bootcamp.transaction.util;

public class Constants {
    /*
     * Base Api Gateway
     * */
    public static final String BASE_URL = "http://localhost:3002";

    /*
     * Microservice Customer
     * */
    public static final String GET_CUSTOMER_DOCUMENT = "/customers/{document}";
    public static final String CUSTOMER_TYPE_PERSONAL = "PERSONAL";
    public static final String CUSTOMER_TYPE_EMPRESARIAL = "EMPRESARIAL";

    /*
    * Microservice Account
    * */
    public static final String GET_ACCOUNT_NUMBER_ACCOUNT = "/accounts/{number}";
    public static final String PUT_ACCOUNT = "/accounts/{number}";
    public static final String ACCOUNT_TYPE_CORRIENTE = "CORRIENTE";

    /*
     * Microservice Credit
     * */
    public static final String GET_CREDIT_ID = "/credits/{id}";
    public static final String PUT_CREDIT = "/credits/{id}";

    /*
     * Microservice Card
     * */
    public static final String GET_CARD_NUMBER_ACCOUNT = "/cards/{number}";
    public static final String PUT_CARD = "/cards/{number}";
}
