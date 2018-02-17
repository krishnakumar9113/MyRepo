package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	Order() {
	}

	public Order(Integer id, Double amount, String items_count,
			String secretCode, String status, Integer userid, Integer vendorid,
			String TimeStamp) {

		this.id = id;
		this.Amount = amount;
		this.Items_count = items_count;
		this.SecretCode = secretCode;
		this.Status = status;
		this.Userid = userid;
		this.VendorID = vendorid;
		this.TimeStamp = TimeStamp;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getItems_count() {
		return Items_count;
	}

	public void setItems_count(String items_count) {
		this.Items_count = items_count;
	}

	@XmlElement
	public String getSecretCode() {
		return SecretCode;
	}

	public void setSecretCode(String secretCode) {
		this.SecretCode = secretCode;
	}

	@XmlElement
	public Double getAmount() {
		return Amount;
	}

	public void setAmount(Double amount) {
		this.Amount = amount;
	}

	@XmlElement
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}

	@XmlElement
	public Integer getUserid() {
		return Userid;
	}

	public void setUserid(Integer userid) {
		this.Userid = userid;
	}

	@XmlElement
	public Integer getVendorID() {
		return VendorID;
	}

	public void setVendorID(Integer vendorid) {
		this.VendorID = vendorid;
	}

	private String Items_count;
	private String SecretCode;
	private String Status;
	private Integer Userid;
	private Integer VendorID;
	private Integer id;
	private Double Amount;
	private String TimeStamp;

	@XmlElement
	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

}
