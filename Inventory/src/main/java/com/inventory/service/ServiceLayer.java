package com.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.entity.InventoryEntity;

@Service
public interface ServiceLayer {

	InventoryEntity productFetched(Integer productId);

	public InventoryEntity productAdded(InventoryEntity inventoryEnt);

	List<InventoryEntity> allInventoriesFetched();

	Integer quantityFetched(Integer productId);

	String quantityUpdated(Integer productId, int quantity);

	void inventoryRemoved(Integer productId);

}
