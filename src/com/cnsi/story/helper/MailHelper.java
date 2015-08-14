package com.cnsi.story.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cnsi.story.constants.StoryConstants;

public class MailHelper {
	public String HOST;
	public String PORT;
	public String AUTHENTICATION;
	public String SENDEREMAIL;
	
	
	public MailHelper(){
		getMailProperties();
	}
	
	public String getMailProperties() {
		String result = "";
		Properties prop = new Properties();
		String propFileName = "mail.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// get the property value and print it out
		HOST = prop.getProperty("mail.smtp.host");
		PORT = prop.getProperty("mail.smtp.auth");
		AUTHENTICATION = prop.getProperty("mail.smtp.port");
		SENDEREMAIL = prop.getProperty("SenderMail");
		return result;
	}

	public void sendUpdateMail(List<StoryConstants> mailList) {
 
		final String username = "pturlapati@cns-inc.com";
		final String password = "Dodger1234$";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", AUTHENTICATION);
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDEREMAIL));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("phani.turlapati@cns-inc.com"));
			message.setSubject("Story Updated");
			
			for(StoryConstants mailText : mailList){
				
				StringBuffer buffer = new StringBuffer();
				
				buffer.append("Your Story No:"+mailText.getStoryID());
				buffer.append("\nhas been updated at"+mailText.getStoryModifiedDate());
				
			/*	message.setText("All,"
						+ "\n\n Your Story No: "+mailText.getStoryID() +" has been updated at "+mailText.getStoryModifiedDate());*/
				if (null != mailText.getStoryTitle()){
					buffer.append("\nTitle: "+mailText.getStoryTitle());
				}else buffer.append(" ");
				if (null != mailText.getStoryBaName()){
					buffer.append("\nBA:"+mailText.getStoryBaName());
				}else buffer.append(" ");
				if (null !=  mailText.getStoryImpactArea()){
					buffer.append("\nImpacted Area:"+mailText.getStoryImpactArea());
				}else buffer.append(" ");
				if (null != mailText.getStoryKey()){
					buffer.append(""+mailText.getStoryKey());
				}else buffer.append(" ");
				if (null != mailText.getStoryDesc()){
					buffer.append("\n Descriprtion:"+mailText.getStoryDesc());
				}else buffer.append(" ");
				if (null != mailText.getStoryItrID()){
					buffer.append(""+mailText.getStoryItrID());
				}else message.setText(" ");buffer.append("");
				if (null != mailText.getStoryItrName()){
					buffer.append("\nIteration Name:"+mailText.getStoryItrName());
				}else buffer.append(" ");
				if (null != mailText.getStoryModifiedBy()){
					buffer.append("\n Modified By"+mailText.getStoryModifiedBy());
				}else buffer.append(" ");
				if (null != mailText.getStoryHours()){
					buffer.append("\n Hours:"+mailText.getStoryHours());
				}else buffer.append(" ");
				if (null != mailText.getStoryBuiltCRNotes()){
					buffer.append("\nBuilt/CR Notes:"+mailText.getStoryBuiltCRNotes());
				}else buffer.append(" ");
				
				message.setText(buffer.toString());
				
			}
			
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
