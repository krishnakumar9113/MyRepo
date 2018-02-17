package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemMobile {

	public ItemMobile(Integer id, String name, String price, Integer count,
			String Vendorname) {

		this.id = id;
		this.Name = name;
		this.Price = price;
		// this.Availability = availability;
		this.count = count;
		this.VendorName = Vendorname;

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
	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
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

	ItemMobile() {
	}

	private Integer id;
	private String Name;
	private String Price;
	// private String Availability;
	private Integer count;
	private String VendorName;

	@XmlElement
	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

}
