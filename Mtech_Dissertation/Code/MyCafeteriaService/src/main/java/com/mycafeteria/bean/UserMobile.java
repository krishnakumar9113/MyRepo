package com.mycafeteria.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserMobile {

	public UserMobile() {
	}

	private User user;

	@XmlElement
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private List<Item> Favs;

	@XmlElement
	public List<Item> getFavs() {
		return Favs;
	}

	public void setFavs(List<Item> favs) {
		Favs = favs;
	}

}
