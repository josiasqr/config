package com.customerjpa.service;

import com.customerjpa.entity.Customer;
import com.customerjpa.entity.Type;

import java.util.List;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomer(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Long id);
    List<Customer> getCustomerType(Type type);
    Customer getCustomerRucdni(String rucdni);
}