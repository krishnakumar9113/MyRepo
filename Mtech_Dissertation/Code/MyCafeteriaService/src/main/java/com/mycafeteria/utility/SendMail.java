package com.mycafeteria.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mycafeteria.bean.Admin;
import com.mycafeteria.businesslogic.AdminFactory;

public class SendMail implements Runnable {
	
	private String tomail;
	private String newpassword;
	AdminFactory adminFactory;

	public SendMail(String tomailid, String newpassword) {
		this.tomail = tomailid;
		this.newpassword = newpassword;
		if (adminFactory == null)
			adminFactory = new AdminFactory();
	}

	// private static String USER_NAME = "mycafeteria.chennai.cts"; // GMail
	// user name (just the part before "@gmail.com")
	// private static String PASSWORD = "2013ht78070"; // GMail password
	// private static String RECIPIENT = "krishnakumar.gopal@yahoo.com";

	public void sendMail() {
		Admin admin = adminFactory.getModel();
		String from = admin.getSMTPMailId();
		String pass = admin.getSMTPPassword();
		String[] to = { tomail }; // list of recipient email addresses
		String subject = "MyCafeteria password reset notification";
		String body = "Thanks for using mycafeteria services.\nYour new password is "
				+ newpassword
				+ ".\nKindly change your password while logging in.\n\n"
				+ "Thanks and Regards \n\n MyCafeteria Admin";

		sendFromGMail(from, pass, to, subject, body);
	}

	private void sendFromGMail(String from, String password, String[] to,
			String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendMail();

	}
}
