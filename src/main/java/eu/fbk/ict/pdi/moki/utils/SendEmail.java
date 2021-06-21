package eu.fbk.ict.pdi.moki.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	public static void send(String to, String sub, String msg){  
        //Get properties object 
		String password = "m0k!d0mpe";
		String from = "moki.dompe@gmail.com";
        Properties props = System.getProperties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        //get Session   
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);    
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         message.setSubject(sub);    
         message.setText(msg);    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {
        	System.out.println(e);
        	throw new RuntimeException(e);
        	}    
           
  }  
	
	
	/*public static void send(String email, String subject, String text) {    
	      // Recipient's email ID needs to be mentioned.
	      String to = email;

	      // Sender's email ID needs to be mentioned
	      String from = "moki-info@fbk.eu";

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties props = System.getProperties();

	      // Setup mail server
	      props.setProperty("mail.smtp.host", host);
	      props.setProperty("mail.smtp.starttls.enable", "false");
	      props.setProperty("mail.smtp.port","25");
	      props.setProperty("mail.smtp.user", "alerts");
	      props.setProperty("mail.smtp.auth", "true");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(props);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(text);

	         // Send message
	         Transport.send(message);
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }*/
}
