package com.inventory.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.inventory.entity.InventoryEntity;
import com.inventory.repo.InventoryRepo;

@Service
public class ServiceLayerImpl implements ServiceLayer {
	@Autowired
	private InventoryRepo inventoryRepo;

	@Override
	public InventoryEntity productFetched(Integer productId) {

		return inventoryRepo.findById(productId).get();
	}

	@Override
	public InventoryEntity productAdded(InventoryEntity inventoryEnt) {
		return inventoryRepo.save(inventoryEnt);
	}

	public List<InventoryEntity> allInventoriesFetched() {
		return inventoryRepo.findAll();
	}

	public Integer quantityFetched(Integer productId) {
		return inventoryRepo.findById(productId).get().getQuantity();
	}

	@Transactional
	public String quantityUpdated(Integer productId, int quantity){
		InventoryEntity inventoryEnt = inventoryRepo.findById(productId).get();
		int oldQuantity = inventoryEnt.getQuantity();
		int newQuantity = oldQuantity - quantity;
		inventoryEnt.setQuantity(newQuantity);
		inventoryRepo.save(inventoryEnt);
		return "Updated Quantity";
	}

	public void inventoryRemoved(Integer productId) {

		inventoryRepo.deleteById(productId);

	}
}
