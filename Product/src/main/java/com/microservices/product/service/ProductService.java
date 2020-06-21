package com.microservices.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microservices.product.dao.ProductDao;
import com.microservices.product.Model.*;

@Service
public class ProductService {
	
	@Value("${product.delete}")
	String proDeleted;
	
	@Value("${product.notexits}")
	String notExits;
	
	@Autowired
	ProductDao repository;

	public List<Product> getAllProduct() {
		return repository.findAll();
	}

	public List<Product> getAllProductByName(String name) {

		List<Product> products = new ArrayList<Product>();
		List<Product> records = new ArrayList<Product>();

		repository.findAll().forEach(records::add);

		for (Product e : records) {
			if (e.getName().equalsIgnoreCase(name)) {
				products.add(e);
			}
		}

		return products;

	}

	public Optional<Product> findOneProduct(Long id) {
		if (repository.existsById(id))
			return repository.findById(id);

		else
			return null;
	}

	public Product addProduct(Product product) {
		return repository.save(product);

	}

	public Product updateProduct(Long id, Product productdetails) throws Throwable {

		Product details = repository.findById(id).orElseThrow(null);

		details.setName(productdetails.getName());
		details.setDescription(productdetails.getDescription());
		details.setCategory(productdetails.getCategory());
		details.setPrice(productdetails.getPrice());
		// details = productdetails;
		final Product updateDetails = repository.save(details);

		// return Response.ok(updateDetails);
		return updateDetails;

	}

	public String deleteProduct(Long id) {

		if (repository.existsById(id)) {
			repository.deleteById(id);
			return proDeleted;
		} else {
			return notExits;
		}
	}
}
