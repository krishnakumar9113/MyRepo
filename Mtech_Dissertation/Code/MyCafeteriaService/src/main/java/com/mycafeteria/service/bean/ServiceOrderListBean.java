package com.mycafeteria.service.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//for generating orders based on user id
@XmlRootElement
public class ServiceOrderListBean {
	public ServiceOrderListBean(Integer orderId, Double amount,
			String vendorName, String TimeStamp) {

		this.OrderId = orderId;
		this.Amount = amount;
		this.VendorName = vendorName;
		// this.OrderStatus = orderStatus;
		this.TimeStamp = TimeStamp;
	}

	ServiceOrderListBean() {
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
	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

	private Integer OrderId;
	private Double Amount;
	
	private String VendorName;
	// private String OrderStatus;
	private String TimeStamp;

	@XmlElement
	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

}
