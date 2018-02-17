package com.mycafeteria.bean;

public class CartItem {

	public CartItem(Item cartItem, int count) {
		super();
		CartItem = cartItem;
		Count = count;
	}
	public Item getCartItem() {
		return CartItem;
	}
	public void setCartItem(Item cartItem) {
		CartItem = cartItem;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	private Item CartItem;
	private int Count;
	
	@Override
	public boolean equals(Object o) {

	   
	    // typecast o to Complex so that we can compare data members 
		CartItem c = (CartItem) o;
		if(this.getCartItem().getId()==c.getCartItem().getId()){
			return true;
		}else{
			return false;
		}
	     
	    
	}

}
