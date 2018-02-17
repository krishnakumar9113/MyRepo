package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	public User(Integer userid, String userName, String password,
			Double declaredAmount, Double availableBalance, String mailid) {

		this.userid = userid;
		this.UserName = userName;
		this.password = password;
		this.DeclaredAmount = declaredAmount;
		this.AvailableBalance = availableBalance;
		this.mailid = mailid;
	}

	User() {
	}

	@XmlElement
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@XmlElement
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public Double getDeclaredAmount() {
		return DeclaredAmount;
	}

	public void setDeclaredAmount(Double declaredAmount) {
		DeclaredAmount = declaredAmount;
	}

	@XmlElement
	public Double getAvailableBalance() {
		return AvailableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		AvailableBalance = availableBalance;
	}

	private Integer userid;
	private String UserName;
	private String password;
	private Double DeclaredAmount;
	private Double AvailableBalance;
	private String mailid;
	private String Favs;
	private String auth_token;

	@XmlElement
	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	@XmlElement
	public String getFavs() {
		// List of item ids
		return Favs;
	}

	public void setFavs(String favs) {
		Favs = favs;
	}

	@XmlElement
	public String getmailid() {
		return mailid;
	}

	public void setmailid(String mail) {
		mailid = mail;
	}

}
