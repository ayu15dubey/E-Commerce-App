package com.services.aggregator.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/product/getoneProduct/"+ id;
		Product product = restTemplate.getForObject(fetchProductbaseurl, Product.class);
		return product;
	}

	@GetMapping("/searchProductbyname/{name}")
	public List<Product> viewProductsByName(@PathVariable("name") String name)
			throws IOException, ClassNotFoundException {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/product/getproductbyname/" + name;
		List<Product> product = restTemplate.getForObject(fetchProductbaseurl, List.class);

		return product;
	}

	@PostMapping("/becomeCustomer")
	public ResponseEntity<Customer> becomeCustomer(@RequestBody Customer customer)
			throws IOException, ClassNotFoundException {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/customer/addcustomer";
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

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/customer/getonecustomer/" + CustId;

		Customer customerObj = restTemplate.getForObject(fetchProductbaseurl, Customer.class);

		return customerObj;

	}

	@PostMapping("/placeOrder/{Productid}/{custid}/{quantity}")
	public ResponseEntity<String> placeOrder(@PathVariable("Productid") Long Productid,
			@PathVariable("custid") Long custid, @PathVariable("quantity") int quantity) {

		Product productCheck = controller.viewProductById(Productid);
		Customer customerCheck = controller.viewMyDetails(custid);
System.out.println(productCheck);
System.out.println(customerCheck);
		if (productCheck != null && customerCheck != null) {
			System.out.println("inside aggr");
			OrderHeader order = new OrderHeader();
order.setProduct(productCheck);
order.setCustomer(customerCheck);
order.setQuantity(quantity);


			RestTemplate restTemplate = restTemplateBuilder.build();

			InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
			String baseurl = insinfo.getHomePageUrl();

			String fetchProductbaseurl = baseurl + "api/order/placeOrder";
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

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/customer/updateonecustomer/" + custId;
		String result = restTemplate.patchForObject(fetchProductbaseurl, customer, String.class);

		return result;

	}

	@DeleteMapping("/deleteorder/{id}")
	public String deleteOrder(@PathVariable("id") int id) {

		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/order/removeOrder/" + id;
		restTemplate.delete(fetchProductbaseurl);
		return "Deleted";

	}

}
