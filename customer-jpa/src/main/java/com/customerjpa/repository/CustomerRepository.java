package com.customerjpa.repository;

import com.customerjpa.entity.Customer;
import com.customerjpa.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByType(Type type);
    Customer findByRucdni(String rucdni);
}
