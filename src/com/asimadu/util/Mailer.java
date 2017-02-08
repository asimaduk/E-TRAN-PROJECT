package com.asimadu.util;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;

import com.asimadu.data.DataAccess;  
  
public class Mailer {  
	
	 public static boolean send(String emailAddress, String identifier, int request) {  
		  boolean status = false;
		  String to = emailAddress;   
		   
		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
				  new javax.mail.Authenticator() {  
			  		protected PasswordAuthentication getPasswordAuthentication() {  
			  			return new PasswordAuthentication("mymail@gmail.com","mypassword"); 
			  		}  
		  });
		  
		  if(request == 1){
			  try {  
				   MimeMessage message = new MimeMessage(session);  
				   message.setFrom(new InternetAddress("asimaduk@gmail.com"));  
				   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				   message.setSubject("Thanks for registering with E-TRAN");  ;
				   message.setContent("<p>Follow the link below to activate your account or <b>IGNORE</b> if you have not"
				   		+ " registerd with us!"
				   		+ "<br>"
				   		+ DataAccess.baseUrl+"Activate?email="+emailAddress+"&identifier="
				   		+ identifier
				   		+ "</p>", "text/html");
				 
				   Transport.send(message);  
				  
				   System.out.println("message sent successfully"); 
				   status = true;
			   
			  } catch (MessagingException e) {
				  status = false; //throw new RuntimeException(e);
			  }
		  }
	    
		  else if(request == 2){
			  try {  
				   MimeMessage message = new MimeMessage(session);
				   message.setFrom(new InternetAddress("mymail@gmail.com"));
				   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				   message.setSubject("You requested for a password reset on E-TRAN");  ;
				   message.setContent("<p>Follow the link below to recover your password or <b>IGNORE</b> if you have not"
				   		+ " requested for password recovery!"
				   		+ "<br>"
				   		+ DataAccess.baseUrl+"RecoverPassword?email="+emailAddress+"&identifier="
				   		+ identifier
				   		+ "</p>", "text/html");
				 
				   Transport.send(message);  
				  
				   System.out.println("message sent successfully"); 
				   status = true;
			   
			  } catch (MessagingException e) {
				  status = false; //throw new RuntimeException(e);
			  }
		  }
		  
		  return status;
	 }  
}  
