package com.services.aggregator.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.LoggingCodecSupport;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.services.aggregator.model.Customer;
import com.services.aggregator.model.OrderHeader;
import com.services.aggregator.model.Product;

@RestController
@RequestMapping("/enduser")
public class Controller extends FallbackMethods {

	@Value("${placeorder.productcheck}")
	String noProductExits;

	@Value("${placeorder.customercheck}")
	String noCustomerExits;

	@Value("${deleteorder.deleted}")
	String deleted;

	@Value("${deleteorder.notexits}")
	String noOrder;

	@Autowired
	Environment env;

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private Controller controller;
	Logger logger = LoggerFactory.getLogger(LoggingCodecSupport.class);

	Boolean hasCustomerBecome = false;

	/**
	 * To view the particular products by id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/searchProductbyid/{id}")
	@HystrixCommand(fallbackMethod = "fallbackViewProductById")
	public Product viewProductById(@PathVariable("id") Long id) {
		logger.info("viewProductById method is invoked");
		RestTemplate restTemplate = restTemplateBuilder.build();

		String serverPort = env.getProperty("local.server.port");
		System.out.println(serverPort);

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();
		String fetchProductbaseurl = baseurl + "api/product/getoneProduct/" + id;
		Product product = restTemplate.getForObject(fetchProductbaseurl, Product.class);
		return product;
	}

	/**
	 * To view the particular products by name.
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@GetMapping("/searchProductbyname/{name}")
	@HystrixCommand(fallbackMethod = "fallbackViewProductsByName")
	public List<Product> viewProductsByName(@PathVariable("name") String name)
			throws IOException, ClassNotFoundException {
		logger.info("viewProductsByName method is invoked");
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();
		String fetchProductbaseurl = baseurl + "api/product/getproductbyname/" + name;
		List<Product> product = restTemplate.getForObject(fetchProductbaseurl, List.class);

		return product;
	}

	/**
	 * To become customer for placing the order
	 * 
	 * @param customer
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@PostMapping("/becomeCustomer")
	@HystrixCommand(fallbackMethod = "fallbackBecomeCustomer")
	public ResponseEntity<Customer> becomeCustomer(@RequestBody Customer customer)
			throws IOException, ClassNotFoundException {

		logger.info("becomeCustomer method is invoked");
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

	/**
	 * To view customer details.
	 * 
	 * @param CustId
	 * @return
	 */
	@GetMapping("/viewmydetails/{custid}")
	@HystrixCommand(fallbackMethod = "fallbackViewMyDetails")
	public Customer viewMyDetails(@PathVariable("custid") Long CustId) {

		logger.info("viewMyDetails method is invoked");
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();
		String fetchProductbaseurl = baseurl + "api/customer/getonecustomer/" + CustId;
		Customer customerObj = restTemplate.getForObject(fetchProductbaseurl, Customer.class);
		return customerObj;

	}

	/**
	 * For placing the order.
	 * 
	 * @param Productid
	 * @param custid
	 * @param quantity
	 * @return
	 */
	@PostMapping("/placeOrder/{Productid}/{custid}/{quantity}")
	@HystrixCommand(fallbackMethod = "fallbackPlaceOrder")
	public ResponseEntity<String> placeOrder(@PathVariable("Productid") Long Productid,
			@PathVariable("custid") Long custid, @PathVariable("quantity") int quantity) {

		logger.info("placeOrder method is invoked");
		Product productCheck = controller.viewProductById(Productid);
		Customer customerCheck = controller.viewMyDetails(custid);

		if (productCheck != null && customerCheck != null) {
			OrderHeader order = new OrderHeader();
			order.setProduct(productCheck);
			order.setCustomer(customerCheck);
			order.setQuantity(quantity);

			RestTemplate restTemplate = restTemplateBuilder.build();
			InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
			String baseurl = insinfo.getHomePageUrl();
			String fetchProductbaseurl = baseurl + "api/order/placeOrder";
			ResponseEntity<String> result = restTemplate.postForEntity(fetchProductbaseurl, order, String.class);
			return result;
		} else if (productCheck == null) {

			String result = noProductExits;
			return new ResponseEntity<String>(result, HttpStatus.CREATED);

		} else {
			String result = noCustomerExits;

			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}

	}

	/**
	 * To view the particular order details.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getmyorder/{orderid}")
	@HystrixCommand(fallbackMethod = "fallbackGetMyOrder")
	public OrderHeader getMyOrder(@PathVariable("orderid") int id) {

		logger.info("getMyOrder method is invoked");
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "api/order/fetchOrder/" + id;
		OrderHeader order = restTemplate.getForObject(fetchProductbaseurl, OrderHeader.class);
		;

		return order;
	}

	/**
	 * TO update the details.
	 * 
	 * @param custId
	 * @param customer
	 * @return
	 */
	@PatchMapping("/updatemydetails/{custid}")
	@HystrixCommand(fallbackMethod = "fallbackUpdateMyDetails")
	public String updateMyDetails(@PathVariable("custid") Long custId, @RequestBody Customer customer) {

		RestTemplate restTemplate = restTemplateBuilder.build();
		logger.info("updateMyDetails method is invoked");

		InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseurl = insinfo.getHomePageUrl();
		String fetchProductbaseurl = baseurl + "api/customer/updateonecustomer/" + custId;
		String result = restTemplate.patchForObject(fetchProductbaseurl, customer, String.class);

		return result;

	}

	/**
	 * Deleting the placed order.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteorder/{id}")
	@HystrixCommand(fallbackMethod = "fallbackDeleteOrder")
	public String deleteOrder(@PathVariable("id") int id) {

		logger.info("deleteOrder method is invoked");
		OrderHeader checkOrder = controller.getMyOrder(id);

		if (checkOrder != null) {
			RestTemplate restTemplate = restTemplateBuilder.build();

			InstanceInfo insinfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
			String baseurl = insinfo.getHomePageUrl();

			String fetchProductbaseurl = baseurl + "api/order/removeOrder/" + id;
			restTemplate.delete(fetchProductbaseurl);
			return deleted;
		}

		else {
			return noOrder;
		}
	}
}
