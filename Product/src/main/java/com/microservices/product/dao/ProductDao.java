package com.microservices.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.product.Model.Product;
@Repository
public interface ProductDao extends JpaRepository<Product, Long>{

}
