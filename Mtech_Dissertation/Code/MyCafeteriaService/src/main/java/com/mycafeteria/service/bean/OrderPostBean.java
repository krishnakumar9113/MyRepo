package com.mycafeteria.service.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//To Post an order from mobile
@XmlRootElement
public class OrderPostBean {
	OrderPostBean() {
	}

	@XmlElement
	public Integer getUserid() {
		return Userid;
	}

	public void setUserid(Integer userid) {
		Userid = userid;
	}

	@XmlElement
	public String getTimeStamp() {
		return TimeStamp;
	}

	@XmlElement
	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

	@XmlElement
	public List<ItemCountBean> getItemcount() {
		return itemcount;
	}

	public void setItemcount(List<ItemCountBean> itemcount) {
		this.itemcount = itemcount;
	}

	private Integer Userid;
	private String TimeStamp;
	private List<ItemCountBean> itemcount;
	private String auth_token;

}
