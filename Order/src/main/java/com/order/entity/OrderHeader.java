package com.order.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderHeader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	/*
	 * private int productId; private String productName;
	 */
	@Embedded
	private Product product;
	private String orderDate;
	@Embedded
	private Customer customer;
	// private int customerId;
	private String orderStatus;
	// private String Address;
	private int quantity;
	private int totalPrice;

	/*
	 * public String getProductName() { return productName; } public void
	 * setProductName(String productName) { this.productName = productName; } public
	 * int getProductId() { return productId; } public void setProductId(int
	 * productId) { this.productId = productId; }
	 */

	public int getOrderId() {
		return orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/*
	 * public int getCustomerId() { return customerId; }
	 * 
	 * public void setCustomerId(int customerId) { this.customerId = customerId; }
	 */

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/*
	 * public String getAddress() { return Address; }
	 * 
	 * public void setAddress(String address) { Address = address; }
	 */
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderHeader(int orderId, Product product, String orderDate, Customer customer, String orderStatus,
			int quantity, int totalPrice) {
		super();
		this.orderId = orderId;
		this.product = product;
		this.orderDate = orderDate;
		this.customer = customer;
		this.orderStatus = orderStatus;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public OrderHeader() {
		super();
		// TODO Auto-generated constructor stub
	}

}
