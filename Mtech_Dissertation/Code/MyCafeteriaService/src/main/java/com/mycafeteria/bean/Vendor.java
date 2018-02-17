package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Vendor implements java.io.Serializable {
	public Vendor(Integer id, String name, String code, String password,
			Integer locationid) {

		this.id = id;
		this.Name = name;
		this.Mail = code;
		this.Password = password;
		this.LocationID = locationid;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	@XmlElement
	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		this.Mail = mail;
	}

	@XmlElement
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	@XmlElement
	public Integer getLocationID() {
		return LocationID;
	}

	public void setLocationID(Integer locationid) {
		this.LocationID = locationid;
	}

	@XmlElement
	public String getLocationName() {
		return LocationName;
	}

	public void setLocationName(String locationName) {
		this.LocationName = locationName;
	}

	Vendor() {
	}

	private Integer id;
	private String Name;
	private String Mail;
	private String Password;
	private Integer LocationID;
	private String LocationName;
	private Boolean isActive;

	@XmlElement
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
