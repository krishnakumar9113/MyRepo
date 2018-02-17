package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item implements java.io.Serializable {
	public Item(Integer id, String name, Double price, Integer count,
			Integer VendorID, Integer categoryID) {

		this.id = id;
		this.Name = name;
		this.Price = price;
		this.count = count;
		this.VendorID = VendorID;
		this.CategoryID = categoryID;
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
	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		this.Price = price;
	}

	/*
	 * @XmlElement public String getAvailability() { return Availability; }
	 * public void setAvailability(String availability) { this.Availability =
	 * availability; }
	 */
	@XmlElement
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@XmlElement
	public Integer getVendorID() {
		return VendorID;
	}

	public void setVendorID(Integer VendorID) {
		this.VendorID = VendorID;
	}

	@XmlElement
	public Integer getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.CategoryID = categoryID;
	}

	Item() {
	}

	private Integer id;
	private String Name;
	private Double Price;
	// private String Availability;
	private Integer count;
	private Integer VendorID;
	private Integer CategoryID;
	private String CategoryName;
	private Boolean IsActive;

	@XmlElement
	public Boolean getIsActive() {
		return IsActive;
	}

	public void setIsActive(Boolean isActive) {
		IsActive = isActive;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	private String VendorName;

	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

}
