package com.mycafeteria.service.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderVendorBean {
	//For order details generation based on vendorId
	public OrderVendorBean(Integer orderId, Double amount, String userName,
			String orderStatus, String TimeStamp) {

		this.OrderId = orderId;
		this.Amount = amount;
		this.UserName = userName;
		// this.VendorName = vendorName;
		this.OrderStatus = orderStatus;
		this.TimeStamp = TimeStamp;
	}

	OrderVendorBean() {
	}

	@XmlElement
	public Integer getOrderId() {
		return OrderId;
	}

	public void setOrderId(Integer orderId) {
		OrderId = orderId;
	}

	@XmlElement
	public Double getAmount() {
		return Amount;
	}

	public void setAmount(Double amount) {
		Amount = amount;
	}

	@XmlElement
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	@XmlElement
	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	private Integer OrderId;
	private Double Amount;
	private String UserName;
	// private String VendorName;
	private String OrderStatus;
	private String TimeStamp;

	@XmlElement
	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

}
