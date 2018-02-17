package com.mycafeteria.bean;

public class Category {

	public Category() {
		super();
	}

	public Category(int id, String category) {
		super();
		this.id = id;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param args
	 */
	private int id;
	private String category;

	@Override
	public String toString() {
		return this.category;
	}
}
