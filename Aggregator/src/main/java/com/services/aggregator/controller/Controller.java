package com.services.aggregator.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.services.aggregator.model.Customer;
import com.services.aggregator.model.OrderHeader;
import com.services.aggregator.model.Product;

@RestController
@RequestMapping("/enduser")
public class Controller {

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private Controller controller;

	Boolean hasCustomerBecome = false;

	@GetMapping("/searchProductbyid/{id}")
	public Product viewProductById(@PathVariable("id") Long id) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("product-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/product/getoneProduct/" + "/" + id;
		Product product = restTemplate.getForObject(fetchProductbaseurl, Product.class);
		return product;
	}

	@GetMapping("/searchProductbyname/{name}")
	public List<Product> viewProductsByName(@PathVariable("name") String name)
			throws IOException, ClassNotFoundException {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("product-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/product/getproductbyname/" + name;
		List<Product> product = restTemplate.getForObject(fetchProductbaseurl, List.class);

		return product;
	}

	@PostMapping("/becomeCustomer")
	public ResponseEntity<Customer> becomeCustomer(@RequestBody Customer customer)
			throws IOException, ClassNotFoundException {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("customer-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/customer/addcustomer";
		ResponseEntity<Customer> customerObj = restTemplate.postForEntity(fetchProductbaseurl, customer,
				Customer.class);

		if (customerObj.getStatusCode().is2xxSuccessful()) {
			hasCustomerBecome = true;
		}

		return customerObj;
	}

	@GetMapping("/viewmydetails/{custid}")
	public Customer viewMyDetails(@PathVariable("custid") Long CustId) {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("customer-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/customer/getonecutomer/" + CustId;

		Customer customerObj = restTemplate.getForObject(fetchProductbaseurl, Customer.class);

		return customerObj;

	}

	@PostMapping("/placeOrder/{Productid}/{custid}/{quantity}")
	public ResponseEntity<String> placeOrder(@PathVariable("Productid") Long Productid,
			@PathVariable("custid") Long custid, @PathVariable("quantity") int quantity) {

		Product productCheck = controller.viewProductById(Productid);
		Customer customerCheck = controller.viewMyDetails(custid);

		if (productCheck != null && customerCheck != null) {
			OrderHeader order = new OrderHeader();

			//Product product = new Product();
			//Customer customer = new Customer();

			product.setId(Productid);
			customer.setCustomerID(custid);

			order.setProduct(productCheck);
			order.setCustomer(customerCheck);
			order.setQuantity(quantity);

			RestTemplate restTemplate = restTemplateBuilder.build();

			InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("order-service", false);
			String baseurl = insinfo.getHomePageUrl();

			String fetchProductbaseurl = baseurl + "/order/placeOrder";
			ResponseEntity<String> customerObj = restTemplate.postForEntity(fetchProductbaseurl, order, String.class);

			return customerObj;

		}

		else if (productCheck == null) {

			String result = "No product exits with this product id";
			return new ResponseEntity<String>(result, HttpStatus.CREATED);

		} else {
			String result = "No Customer exits with this Customer id \r\n --Please become customer for any order--"
					+ "\r\n Url : http://localhost:8080/enduser/becomeCustomer";

			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}

	}

	@PatchMapping("/updatemydetails/{custid}")
	public String updateMyDetails(@PathVariable("custid") Long custId, @RequestBody Customer customer) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("customer-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/customer/updateonecustomer/" + custId;
		String result = restTemplate.patchForObject(fetchProductbaseurl, customer, String.class);

		return result;

	}

	@DeleteMapping("/deleteorder")
	public String deleteOrder(@PathVariable("id") int id) {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("order-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/order/removeOrder/" + id;
		String result = restTemplate.getForObject(fetchProductbaseurl, String.class);
		return result;

	}

}
