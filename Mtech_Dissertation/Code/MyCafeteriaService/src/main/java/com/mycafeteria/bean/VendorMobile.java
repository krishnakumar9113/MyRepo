package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VendorMobile implements java.io.Serializable {
	public VendorMobile(Integer id, String name) {

		this.id = id;
		this.Name = name;

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

	VendorMobile() {
	}

	private Integer id;
	private String Name;

}
