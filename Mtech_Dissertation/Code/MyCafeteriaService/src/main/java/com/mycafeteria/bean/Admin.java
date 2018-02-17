package com.mycafeteria.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Admin implements java.io.Serializable {
	Admin() {
	}

	public Admin(Integer id, String mailId, String password) {

		this.id = id;
		this.MailId = mailId;
		this.Password = password;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getMailId() {
		return MailId;
	}

	public void setMailId(String mailId) {
		MailId = mailId;
	}

	@XmlElement
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	private Integer id;
	private String MailId;
	private String Password;
	private String SMTPMailId;

	@XmlElement
	public String getSMTPMailId() {
		return SMTPMailId;
	}

	public void setSMTPMailId(String sMTPMailId) {
		SMTPMailId = sMTPMailId;
	}

	@XmlElement
	public String getSMTPPassword() {
		return SMTPPassword;
	}

	public void setSMTPPassword(String sMTPPassword) {
		SMTPPassword = sMTPPassword;
	}

	private String SMTPPassword;

}
