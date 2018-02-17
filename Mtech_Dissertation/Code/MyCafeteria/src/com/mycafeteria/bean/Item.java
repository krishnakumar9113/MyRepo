package com.mycafeteria.bean;

public class Item {
	Item(){}
public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
public Item(int id, String name, Double price,boolean isSelected,int count,boolean isfav,String VendorName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.isSelected=isSelected;
		this.count=count;
		this.isfav=isfav;
		this.VendorName=VendorName;
	}
private int id;
private String name;
private Double price;
private boolean isSelected;
private boolean isfav;
public boolean isIsfav() {
	return isfav;
}
public void setIsfav(boolean isfav) {
	this.isfav = isfav;
}
private int count;
private String VendorName;

public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public String getVendorName() {
	return VendorName;
}
public void setVendorName(String vendorName) {
	VendorName = vendorName;
}
public boolean isSelected() {
	return isSelected;
}
public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
}
@Override
public String toString() {
    return  this.name;
}
@Override
public boolean equals(Object object) {

   
    // typecast o to Complex so that we can compare data members 
	Item item = (Item) object;
	if(this.id==item.id){
		return true;
	}else{
		return false;
	}
     
    
}
}
