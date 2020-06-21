package com.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventoryEntity {
	@Id
	private int productId;
	private int storeId;
	private int quantity;
	private String city;
	private String Location;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	/*
	 * public InventoryEntity() { super(); // TODO Auto-generated constructor stub }
	 * 
	 * public InventoryEntity(Integer productId, int storeId, int quantity, String
	 * city, String location) { super(); this.productId = productId; this.storeId =
	 * storeId; this.quantity = quantity; this.city = city; Location = location; }
	 */
	
}
