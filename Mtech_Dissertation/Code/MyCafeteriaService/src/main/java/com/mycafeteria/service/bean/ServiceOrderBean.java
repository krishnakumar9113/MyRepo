package com.mycafeteria.service.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//For generating complete order details for KIOSK Application
@XmlRootElement
public class ServiceOrderBean {
	public ServiceOrderBean(Integer orderId, Double amount,
			List<ServiceItemBean> itemList, String secretCode, String userName,
			String vendorName, String orderStatus, String TimeStamp) {

		this.OrderId = orderId;
		this.Amount = amount;
		this.ItemList = itemList;
		this.SecretCode = secretCode;
		this.UserName = userName;
		this.VendorName = vendorName;
		this.OrderStatus = orderStatus;
		this.TimeStamp = TimeStamp;
	}

	ServiceOrderBean() {
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
	public List<ServiceItemBean> getItemList() {
		return ItemList;
	}

	public void setItemList(List<ServiceItemBean> itemList) {
		ItemList = itemList;
	}

	@XmlElement
	public String getSecretCode() {
		return SecretCode;
	}

	public void setSecretCode(String secretCode) {
		SecretCode = secretCode;
	}

	@XmlElement
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	@XmlElement
	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
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
	private List<ServiceItemBean> ItemList;
	private String SecretCode;
	private String UserName;
	private String VendorName;
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
