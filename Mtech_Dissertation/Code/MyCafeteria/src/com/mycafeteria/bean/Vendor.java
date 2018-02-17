package com.mycafeteria.bean;

public class Vendor {

	public Vendor() {
	}

	public Vendor(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int id;
	private String name;

	@Override
	public String toString() {
		return this.name;
	}
}
