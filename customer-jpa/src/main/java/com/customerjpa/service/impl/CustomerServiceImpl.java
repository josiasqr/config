package com.customerjpa.service.impl;

import com.customerjpa.entity.Customer;
import com.customerjpa.entity.Type;
import com.customerjpa.repository.CustomerRepository;
import com.customerjpa.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Register customer: " + customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer cus = getCustomer(customer.getId());

        if(null == cus){
            return null;
        }
        cus.setName(customer.getName());
        cus.setRucdni(customer.getRucdni());
        cus.setType(customer.getType());

        return customerRepository.save(cus);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getCustomerType(Type type) {
        return customerRepository.findByType(type);
    }

    @Override
    public Customer getCustomerRucdni(String rucdni) {
        return customerRepository.findByRucdni(rucdni);
    }
}
