package com.microservices.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.product.service.ProductService;
import com.microservices.product.Model.*;;


@RestController
public class ProductController {

	@Autowired
	ProductService service;
	
	@PostMapping("/addproduct")
	public Product addProduct(@RequestBody Product product) {
		return service.addProduct(product);
	}
	
	@GetMapping("/getallproduct")
	public List<Product> getAllProduct(){
		return service.getAllProduct();
	}
	
	@GetMapping("/getoneProduct/{id}")
	public Optional<Product> getOneProduct(@PathVariable("id") Long id) {
		return service.findOneProduct(id);
	}
	
	@GetMapping("/getproductbyname/{name}")
	public List<Product> getProductsByName(@PathVariable("name") String name){
		return service.getAllProductByName(name);
	}
	
	@PutMapping("/updateoneproduct/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws Throwable {
		return service.updateProduct(id, product);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteProductById(@PathVariable("id") Long id) {
		return service.deleteProduct(id);
	}	
	
}
