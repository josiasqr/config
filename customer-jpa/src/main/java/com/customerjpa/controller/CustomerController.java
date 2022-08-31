package com.customerjpa.controller;

import com.customerjpa.entity.Customer;
import com.customerjpa.entity.Type;
import com.customerjpa.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private static final Logger logger = LogManager.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers(@RequestParam(name = "typeId", required = false) Integer typeId){
        List<Customer> cus;

        if(null == typeId){
            cus = customerService.listCustomers();

            if(cus.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            cus = customerService.getCustomerType(Type.builder().id(typeId).build());
            if(cus.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(cus);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        Customer cus = customerService.getCustomer(id);
        if(null == cus){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cus);
    }

    @GetMapping("/rucdni/{rucdni}")
    public ResponseEntity<Customer> getCustomerRucdni(@PathVariable("rucdni") String rucdni){
        Customer cus = customerService.getCustomerRucdni(rucdni);
        if(null == cus){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cus);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer cus = customerService.createCustomer(customer);
        //return new ResponseEntity(customerService.createCustomer(customer), HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(cus);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        customer.setId(id);
        Customer cus = customerService.updateCustomer(customer);

        if(null == cus){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cus);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id){
        Customer cus = customerService.getCustomer(id);

        if(null == cus){
            return ResponseEntity.notFound().build();
        }
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
