package com.inventory.controller;

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

import com.inventory.entity.InventoryEntity;
import com.inventory.service.ServiceLayer;

@RestController
public class InventoryController {


	@Autowired
	private ServiceLayer serviceLayer;

	Logger logger=LoggerFactory.getLogger(LoggingCodecSupport.class);
	
	/**
	 * Fetches the Inventory for given id
	 * @param productId
	 * @return
	 */
	@GetMapping("/fetchProduct/{productId}")
	ResponseEntity<InventoryEntity> productFetched(@PathVariable int productId) {
		logger.info("productFetched method is invoked");
		return ResponseEntity.ok(serviceLayer.productFetched(productId));
	}

	/**
	 * Accepts Inventory Data and creates an Inventory 
	 * @param inventoryEnt
	 * @return
	 */
	@PostMapping("/addProduct")
	public ResponseEntity<InventoryEntity> productAdded(@RequestBody InventoryEntity inventoryEnt) {
		logger.info("productAdded method is invoked");
		return new ResponseEntity<InventoryEntity>(serviceLayer.productAdded(inventoryEnt), HttpStatus.CREATED);
	}

	/**
	 * Fetches All Inventories from DB
	 * @return
	 */
	@GetMapping("/fetchAllInventories")
	ResponseEntity<List<InventoryEntity>> allInventoriesFetched() {
		logger.info("allInventoriesFetched method is invoked");
		return new ResponseEntity<List<InventoryEntity>>(serviceLayer.allInventoriesFetched(), HttpStatus.OK);

	}

	/**
	 * Fetches Quantity of Product based on given id
	 * @param productId
	 * @return
	 */
	@GetMapping("/quantityFetch/{productId}")
	ResponseEntity<Integer> quantityFetched(@PathVariable int productId) {
		logger.info("quantityFetched method is invoked");
		return ResponseEntity.ok(serviceLayer.quantityFetched(productId));
	}

	/**
	 * Updates the Quantity of Product by given id
	 * @param productId
	 * @param quantity
	 * @return
	 */
	@PutMapping("/updateQuantity/{productId}/{quantity}")
	ResponseEntity<String> quantityUpdated(@PathVariable int productId, @PathVariable int quantity) {
		logger.info("quantityUpdated method is invoked");
		return new ResponseEntity<String>(serviceLayer.quantityUpdated(productId, quantity), HttpStatus.ACCEPTED);
	}

	/**
	 * Removes the Inventory based on given id
	 * @param productId
	 * @return
	 */
	@DeleteMapping("/removeInventory/{productId}")
	ResponseEntity<String> inventoryRemoved(@PathVariable int productId) {
		logger.info("inventoryRemoved method is invoked");
		serviceLayer.inventoryRemoved(productId);
		return ResponseEntity.ok("Inventory Removed");
	}

}
