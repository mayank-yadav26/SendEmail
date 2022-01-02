package com.emailsend;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class App 
{
	public static void main( String[] args )
	{
		System.out.println( "Preparing to send message" );
		String message = "<h1>Hello Dear Hapyy New Year!</h1><br><p>Hurrey!!</p>";
		String subject = "New year wish";
		String to = "mayankyadavcool1503@gmail.com";
		String from = "thunderstorm4627@gmail.com";
		String filePath = "C:\\Users\\mayank.yadav\\Desktop\\extra\\wallpaperflare.com_wallpaper.jpg";

		if(filePath.isEmpty() || filePath.equals("") || filePath==null) {
			sendEmail(message,subject,to,from);
		}else {
			sendEmailWithAttachment(filePath,message,subject,to,from);
		}
	}

	private static void sendEmailWithAttachment(String filePath,String message, String subject, String to, String from) {
		try {

			MimeMessage mimeMessage = settingAllInformation(message,subject,to,from);
			MimeMultipart mimeMultipart = new MimeMultipart();
			//text 
			//file 
			MimeBodyPart textMime = new MimeBodyPart();
			//for sending normal message without html
			//textMime.setText(message);
			
			//for sending html content
			textMime.setText(message,null,"html");

			MimeBodyPart fileMime = new MimeBodyPart();
			fileMime.attachFile(filePath);

			mimeMultipart.addBodyPart(textMime);
			mimeMultipart.addBodyPart(fileMime);

			mimeMessage.setContent(mimeMultipart);
			Transport.send(mimeMessage);
			System.out.println("Message sent successfully with attachment");
		}catch(Exception e) {
			System.out.println("Exception in sendAttachment"+e.getMessage());
		}
	}

	private static void sendEmail(String message, String subject, String to, String from) {
		try {
			MimeMessage mimeMessage = settingAllInformation(message,subject,to,from);
			Transport.send(mimeMessage);
		}catch(Exception e) {
			System.out.println("Exception in sendEmail : "+e.getMessage());
		}
	}

	private static MimeMessage settingAllInformation(String message, String subject, String to, String from) {

		String host = "smtp.gmail.com";

		//get system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);

		//setting important information to properties object 

		//hot set 
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");

		//Step 1 : to get session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("thunderstorm4627@gmail.com","thunder@123&");
			}
		});

		session.setDebug(true);

		//Step 2 : compose the message
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(from);
			//adding recipient to message
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//adding subject to message
			mimeMessage.setSubject(subject);
			//adding text to message
			mimeMessage.setText(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return mimeMessage;
	}
}
