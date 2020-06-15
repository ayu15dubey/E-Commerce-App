package com.order.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Customer {

	private Long customerID;
	private String custName;
	private Long phoneNumber;
	private String email;
	
	@Embedded
	private CustAddress address;

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String firstName) {
		this.custName = firstName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustAddress getAddress() {
		return address;
	}

	public void setAddress(CustAddress address) {
		this.address = address;
	}

	public Customer(Long customerID, String custName, Long phoneNumber, String email, CustAddress address) {
		super();
		this.customerID = customerID;
		this.custName = custName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}

	public Customer() {
		super();
		// TODO Auto-gene
	}
}
