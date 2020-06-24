package com.services.aggregator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.services.aggregator.model.Customer;
import com.services.aggregator.model.OrderHeader;
import com.services.aggregator.model.Product;

public class FallbackMethods {
	
	public Product fallbackViewProductById(Long id) {
		return new Product(id, "Not found", "Not found", null, "Not found");
	}

	public List<Product> fallbackViewProductsByName(String name) {
		List<Product> products = new ArrayList<Product>();
		Product product = new Product(null, "Not found", "Not found", null, "Not found");
		products.add(product);
		return products;
	}

	public ResponseEntity<Customer> fallbackBecomeCustomer(Customer customer) {
		return new ResponseEntity<Customer>(customer, HttpStatus.ACCEPTED);
	}

	public Customer fallbackViewMyDetails(Long CustId) {
		return new Customer();
	}

	public ResponseEntity<String> fallbackPlaceOrder(Long Productid, Long custid, int quantity) {
		String result = "Cannot process";
		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}

	public OrderHeader fallbackGetMyOrder(int id) {
		return new OrderHeader();
	}

	public String fallbackUpdateMyDetails(Long custId, Customer customer) {
		return "Cannot process";
	}

	public String fallbackDeleteOrder(int id) {
		return "Cannot process";
	}
}
