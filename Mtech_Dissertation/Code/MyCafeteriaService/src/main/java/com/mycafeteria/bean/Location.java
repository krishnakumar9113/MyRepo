package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location implements java.io.Serializable {
	Location() {
	}

	public Location(Integer id, String location, String code) {

		this.id = id;
		this.Location = location;
		this.Code = code;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	private Integer id;
	private String Location;
	private String Code;

	@XmlElement
	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		this.Code = code;
	}

}
