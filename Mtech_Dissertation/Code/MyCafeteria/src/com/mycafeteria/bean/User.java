package com.mycafeteria.bean;

public class User {
	private int id;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	private Double Avail_Amt;
	public Double getAvail_Amt() {
		return Avail_Amt;
	}


	public void setAvail_Amt(Double avail_Amt) {
		Avail_Amt = avail_Amt;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	private String pwd;
	private String auth_Token;
	public String getAuth_Token() {
		return auth_Token;
	}


	public void setAuth_Token(String auth_Token) {
		this.auth_Token = auth_Token;
	}


	public User( int id,String userName, String mail,String pwd, Double decl_Amt,Double avail_Amt,String auth_token) {
		this.id=id;
		this.UserName = userName;
		this.mailid=mail;
		this.pwd = pwd;
		this.Decl_Amt = decl_Amt;
		this.Avail_Amt = avail_Amt;
		this.auth_Token=auth_token;
	}
	
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	public Double getDecl_Amt() {
		return Decl_Amt;
	}
	public void setDecl_Amt(Double decl_Amt) {
		Decl_Amt = decl_Amt;
	}
	private String UserName;
	private String mailid;
	public String getmailid() {
		return mailid;
	}


	public void setmailid(String mail) {
		mailid= mail;
	}
	private Double Decl_Amt;
	private String favlist;
	public String getFavlist() {
		return favlist;
	}


	public void setFavlist(String favlist) {
		this.favlist = favlist;
	}

}
