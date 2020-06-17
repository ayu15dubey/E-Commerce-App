package com.inventory.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.entity.InventoryEntity;
import com.inventory.repo.InventoryRepo;
import com.inventory.service.ServiceLayer;

@RestController
public class InventoryController {

	@Autowired
	private InventoryRepo inventoryRepo;
	@Autowired
	private ServiceLayer serviceLayer;

	@GetMapping("/fetchProduct/{productId}")
	ResponseEntity<InventoryEntity> productFetched(@PathVariable int productId) {

		return ResponseEntity.ok(serviceLayer.productFetched(productId));
	}

	@PostMapping("/addProduct")
	public ResponseEntity<InventoryEntity> productAdded(@RequestBody InventoryEntity inventoryEnt) {
		return new ResponseEntity<InventoryEntity>(serviceLayer.productAdded(inventoryEnt), HttpStatus.CREATED);
	}

	@GetMapping("/fetchAllInventories")
	ResponseEntity<List<InventoryEntity>> allInventoriesFetched() {

		return new ResponseEntity<List<InventoryEntity>>(serviceLayer.allInventoriesFetched(), HttpStatus.OK);

	}

	@GetMapping("/quantityFetch/{productId}")
	ResponseEntity<Integer> quantityFetched(@PathVariable int productId) {

		return ResponseEntity.ok(serviceLayer.quantityFetched(productId));
	}

	@PutMapping("/updateQuantity/{productId}/{quantity}")
	ResponseEntity<String> quantityUpdated(@PathVariable int productId, @PathVariable int quantity) {

		return new ResponseEntity<String>(serviceLayer.quantityUpdated(productId, quantity), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/removeInventory/{productId}")
	ResponseEntity<String> inventoryRemoved(@PathVariable int productId) {
		serviceLayer.inventoryRemoved(productId);
		return ResponseEntity.ok("Inventory Removed");
	}

}
