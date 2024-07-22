package sk.dominanz.domail.smtp;

import java.util.Properties;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sk.dominanz.domail.Configuration;

public class Examples {

	 
    /**
	 * Get email message (MimeMessage) for sending
	 * @param session
	 * @return
	 * @throws MessagingException
	 */
	private MimeMessage GetMessage(Session session) throws MessagingException {

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(Configuration.MailFrom));

		// Set To: header field of the header.
		message.setRecipient(RecipientType.TO, new InternetAddress(Configuration.MailTo));

		// Set Subject: header field
		message.setSubject(Configuration.MailSubject);

		// Now set the actual message
		message.setText("Test SMTP body from Java");

		return message;
	}
	

    /**
     * Send email plain 25 port without encryption
     */
    public void exampleSmtp_EmailSend1()
    {
    	try {   		
    		Properties properties = new Properties();
    		properties.setProperty("mail.smtp.host", Configuration.URL);
    		properties.setProperty("mail.smtp.port", "25");
    		properties.setProperty("mail.smtp.auth", "false");  

    		Session session = Session.getDefaultInstance(properties);
    		
    		MimeMessage message = GetMessage(session);
	    	
	    	// Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
    	} catch (MessagingException mex) {
    		mex.printStackTrace();
    	}
    }
    
    
    /**
     * Send email through SMTP with TSL
     */
    public void exampleSmtp_EmailSend2()
    {
    	try {
    		Properties properties = new Properties();
    		properties.put("mail.smtp.host", Configuration.URL);
    		properties.put("mail.smtp.port", "587");
    		properties.put("mail.smtp.auth", "true");    		
    		properties.put("mail.smtp.starttls.enable","true");//TLS
    			
    		Session session = Session.getInstance(properties,
    	            new javax.mail.Authenticator() {
    	                protected PasswordAuthentication getPasswordAuthentication() {
    	                    return new PasswordAuthentication(Configuration.MailFrom, Configuration.SMTP_password);
    	                }
    	            });
    		
    		MimeMessage message = GetMessage(session);
    		// Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
    	} catch (MessagingException mex) {
    		mex.printStackTrace();
    	}
    }
    
   
   
    
    /**
     * Send email through SMTP with SSL
     */
    public void exampleSmtp_EmailSend3()
    {
    	try {
    		Properties properties = new Properties();
    		properties.put("mail.smtp.host", Configuration.URL);
    		properties.put("mail.smtp.port", "465");    		    		
    		properties.put("mail.smtp.auth", "true");
    		properties.put("mail.smtp.starttls.enable","true");
    		properties.put("mail.smtp.socketFactory.port", "465");
    		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    		    		
    		Session session = Session.getInstance(properties,
    	            new javax.mail.Authenticator() {
    	                protected PasswordAuthentication getPasswordAuthentication() {
    	                    return new PasswordAuthentication(Configuration.MailFrom, Configuration.SMTP_password);
    	                }
    	            });

    		MimeMessage message = GetMessage(session);
	    	
	    	// Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
    	} catch (MessagingException mex) {
    		mex.printStackTrace();
    	}
    }
    
    
}
