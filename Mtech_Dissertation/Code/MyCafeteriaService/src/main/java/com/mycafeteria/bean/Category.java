package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Category implements java.io.Serializable {
	Category() {
	}

	public Category(Integer id, String category) {

		this.id = id;
		this.CategoryName = category;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String category) {
		CategoryName = category;
	}

	private Integer id;
	private String CategoryName;

}
