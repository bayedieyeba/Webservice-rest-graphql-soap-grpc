package com.baye.exemplecustomerservice.repository;

import com.baye.exemplecustomerservice.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
