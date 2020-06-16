package com.microservices.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.customer.model.Customer;
@Repository
public interface CustomerDao extends JpaRepository<Customer, Long>{

}
