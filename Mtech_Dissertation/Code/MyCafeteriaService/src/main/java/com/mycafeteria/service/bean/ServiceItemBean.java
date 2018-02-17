package com.mycafeteria.service.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//For generating Item Details in orders 
@XmlRootElement
public class ServiceItemBean {
	ServiceItemBean() {
	}

	@XmlElement
	public String getItemName() {
		return ItemName;
	}

	@XmlElement
	public Integer getItemCount() {
		return ItemCount;
	}

	public ServiceItemBean(String itemName, Integer itemCount, Double Price) {

		ItemName = itemName;
		ItemCount = itemCount;
		price = Price;
	}

	private String ItemName;

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public void setItemCount(Integer itemCount) {
		ItemCount = itemCount;
	}

	private Integer ItemCount;
	private Double price;

	@XmlElement
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
