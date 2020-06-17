package com.order.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.order.entity.CustAddress;
import com.order.entity.OrderHeader;
import com.order.repo.OrderRepo;
import com.order.serviceLayer.ServiceLayer;

@RestController
public class OrderController {

	@Autowired
	private ServiceLayer serviceLayer;

	@PostMapping("/placeOrder")
	ResponseEntity<String> orderPlaced(@RequestBody OrderHeader orderHead) {

		return new ResponseEntity<String>(serviceLayer.orderPlaced(orderHead), HttpStatus.CREATED);
	}

	@PutMapping("/updateOrder/{orderId}/")
	ResponseEntity<String> orderUpdated(@RequestBody CustAddress address, @PathVariable int orderId) {

		return new ResponseEntity<String>(serviceLayer.orderUpdated(address, orderId), HttpStatus.OK);
	}

	@GetMapping("/fetchOrder/{orderId}")
	ResponseEntity<OrderHeader> orderFetched(@PathVariable int orderId) {

		return new ResponseEntity<OrderHeader>(serviceLayer.orderFetched(orderId), HttpStatus.OK);

	}

	@GetMapping("/fetchAllOrder")
	ResponseEntity<List<OrderHeader>> allOrderFetched() {

		return new ResponseEntity<List<OrderHeader>>(serviceLayer.allOrderFetched(), HttpStatus.OK);

	}

	@DeleteMapping("/removeOrder/{orderId}")
	ResponseEntity<String> orderRemoved(@PathVariable int orderId) {

		return new ResponseEntity<String>(serviceLayer.orderRemoved(orderId), HttpStatus.OK);

	}

}
