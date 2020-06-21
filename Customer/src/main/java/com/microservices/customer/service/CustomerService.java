package com.microservices.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.customer.dao.CustomerDao;
import com.microservices.customer.model.CustAddress;
import com.microservices.customer.model.Customer;

@Service
public class CustomerService {

	@Autowired
	CustomerDao repository;

	public List<Customer> getAllCustomer() {
		return repository.findAll();
	}

	public List<Customer> getAllCustomerByName(String name) {

		List<Customer> customer = new ArrayList<Customer>();
		List<Customer> records = new ArrayList<Customer>();

		repository.findAll().forEach(records::add);

		for (Customer e : records) {
			if (e.getCustName().equalsIgnoreCase(name)) {
				customer.add(e);
			}
		}

		return customer;

	}

	public Optional<Customer> findOneCustomer(Long id) {
		if (repository.existsById(id))
			return repository.findById(id);

		else
			return null;
	}

	public Customer addCustomer(Customer customer) {
		return repository.save(customer);

	}

	public String updateCustomer(Long id, Customer customerdetails) throws Throwable {

		Customer details = repository.findById(id).orElseThrow(null);
		CustAddress address = details.getAddress();

		details.setCustName(customerdetails.getCustName());
		details.setEmail(customerdetails.getEmail());
		details.setPhoneNumber(customerdetails.getPhoneNumber());

		address.setCity(customerdetails.getAddress().getCity());
		address.setCountry(customerdetails.getAddress().getCountry());
		address.setPinCode(customerdetails.getAddress().getPinCode());
		address.setState(customerdetails.getAddress().getState());

		details.setAddress(address);

		// details = customerdetails;

		final Customer updateDetails = repository.save(details);
		return "Details updated";

	}

	public String deleteCustomer(Long id) {

		if (repository.existsById(id)) {
			repository.deleteById(id);
			return "deleted";
		} else {
			return "Id doesnt exits";
		}
	}
}
