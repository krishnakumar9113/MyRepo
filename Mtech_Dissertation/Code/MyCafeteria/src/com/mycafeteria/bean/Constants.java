package com.mycafeteria.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

	public static Integer SelectedLocation;
	private static boolean isLoggedIn=false;
	private static User CurrentUser;
	public static HashMap<String,Integer> LocationMap= new HashMap<String,Integer>();
public static String IP = "mycafeteria.elasticbeanstalk.com" ;
//public static String IP="192.168.1.4:8080/mycafeteria";
//public static String IP="192.168.20.74:8080/mycafeteria";
//	public static String IP="192.168.43.174:8080/mycafeteria";//mobile
	public static DecimalFormat df;
	public static User getCurrentUser() {
		return CurrentUser;
	}
	public static void setCurrentUser(User currentUser) {
		CurrentUser = currentUser;
	}
	public static boolean isLoggedIn() {
		return isLoggedIn;
	}
	public static void setLoggedIn(boolean isLoggedIn) {
		Constants.isLoggedIn = isLoggedIn;
	}
	private static List<CartItem> cart ;
	public static List<Item> favourites;
	public static List<Item> getFavourites() {
		if(favourites==null){
			favourites= new ArrayList<Item>();
		}
	
		return favourites;
	}
	public static void setFavourites(List<Item> favourites) {
		Constants.favourites = favourites;
	}
	public static String locationString;
	public static List<CartItem> getCart() {
		if(cart==null){
			cart= new ArrayList<CartItem>();
		}
		return cart;
	}
	public static void setCart(List<CartItem> cart) {
		Constants.cart = cart;
	}
	
}
