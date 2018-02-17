package com.mycafeteria.bean;

public class Order {

	public Order(Double amount, int orderID, String vendorName, String timestamp) {
		super();
		this.Amount = amount;
		this.OrderID = orderID;
		this.VendorName = vendorName;
		this.timestamp = timestamp;
	}
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public String getVendorName() {
		return VendorName;
	}
	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	Double Amount;
	int OrderID;
	String VendorName;
	String timestamp;
}
