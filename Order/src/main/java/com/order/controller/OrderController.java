package com.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.order.entity.CustAddress;
import com.order.entity.OrderHeader;
import com.order.serviceLayer.ServiceLayer;

@RestController
public class OrderController {

	@Autowired
	private ServiceLayer serviceLayer;
	Logger logger=LoggerFactory.getLogger(LoggingCodecSupport.class);

	/**
	 * Accepts order data and Places the Order
	 * @param orderHead
	 * @return
	 */
	@PostMapping("/placeOrder")
	ResponseEntity<String> orderPlaced(@RequestBody OrderHeader orderHead) {
logger.info("orderplaced method is invoked");
		return new ResponseEntity<String>(serviceLayer.orderPlaced(orderHead), HttpStatus.CREATED);
	}

	/**
	 * Updates the particular order with given address 
	 * @param address
	 * @param orderId
	 * @return
	 */
	@PutMapping("/updateOrder/{orderId}/")
	ResponseEntity<String> orderUpdated(@RequestBody CustAddress address, @PathVariable int orderId) {
		logger.info("orderUpdated method is invoked");
		return new ResponseEntity<String>(serviceLayer.orderUpdated(address, orderId), HttpStatus.OK);
	}

	/**
	 * Fetches the order for given id
	 * @param orderId
	 * @return
	 */
	@GetMapping("/fetchOrder/{orderId}")
	ResponseEntity<OrderHeader> orderFetched(@PathVariable int orderId) {
		logger.info("orderFetched method is invoked");
		return new ResponseEntity<OrderHeader>(serviceLayer.orderFetched(orderId), HttpStatus.OK);

	}

	/**
	 * Fetches all Orders from DB
	 * @return
	 */
	@GetMapping("/fetchAllOrder")
	ResponseEntity<List<OrderHeader>> allOrderFetched() {
		logger.info("allOrderFetched method is invoked");
		return new ResponseEntity<List<OrderHeader>>(serviceLayer.allOrderFetched(), HttpStatus.OK);

	}

	/**
	 * Removes the order for the given id
	 * @param orderId
	 * @return
	 */
	@DeleteMapping("/removeOrder/{orderId}")
	ResponseEntity<String> orderRemoved(@PathVariable int orderId) {
		logger.info("orderRemoved method is invoked");
		return new ResponseEntity<String>(serviceLayer.orderRemoved(orderId), HttpStatus.OK);

	}

}
