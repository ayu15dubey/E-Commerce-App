package com.microservices.customer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.customer.model.Customer;
import com.microservices.customer.service.CustomerService;
@RestController
public class CustomerController {

	@Autowired
	CustomerService service;
	
	@PostMapping("/addcustomer")
	public Customer addCustomer(@RequestBody Customer customer) {
		return service.addCustomer(customer);
	}
	
	@GetMapping("/getallcustomer")
	public List<Customer> getAllcustomer(){
		return service.getAllCustomer();
	}
	
	@GetMapping("/getonecustomer/{id}")
	public Optional<Customer> getOneCustomer(@PathVariable("id") Long id) {
		return service.findOneCustomer(id);
	}
	
	@GetMapping("/getcustomerbyname/{name}")
	public List<Customer> getCustomersByName(@PathVariable("name") String name){
		return service.getAllCustomerByName(name);
	}
	
	@PatchMapping("/updateonecustomer/{id}")
	public String updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customerdetails) throws Throwable {
		return service.updateCustomer(id, customerdetails);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteProductById(@PathVariable("id") Long id) {
		return service.deleteCustomer(id);
	}	
	
	
}
