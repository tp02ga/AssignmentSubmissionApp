package com.coderscampus.AssignmentSubmissionApp.service;

import java.util.Properties;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;

@Service
public class NotificationService {
	@Value("${gmail.username}") 
		  String username;
	@Value("${gmail.password}") 
	      String password;
	        @Value("#{'${code.reviewer.emails}'.split(',')}") 
		      String codeReviewer;
		  
		  public void sendAssignmentStatusUpdateStudent(String oldStatus, Assignment assignment) {
	      String link = "";
	      if(assignment.getStatus().contentEquals("COMPLETED")) {
	    	  link = assignment.getCodeReviewVideoUrl();
	      }
	      String emailMessageStudent ="Hello" + assignment.getUser().getName()+", "+
          "\n\n Your assignment " + assignment.getNumber()+ " has gone from " +oldStatus+"to"+assignment.getStatus()+" " + link;

	        Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse(assignment.getUser().getUsername())
	            );
	            message.setSubject("Assignment Status Update");
	            message.setText(emailMessageStudent);

	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
		  
		  public void sendAssignmentStatusUpdateCodeReviewer(String oldStatus, Assignment assignment) {
		      String link = "";
		      if(assignment.getStatus().contentEquals("SUBMITTED")) {
		    	  link = assignment.getGithubUrl();
		      }
		      if(assignment.getStatus().contentEquals("RESUBMITTED")) {
		    	  codeReviewer = assignment.getCodeReviewer().getUsername();
		      }
		    String emailMessage = "Hello, " + assignment.getUser().getName()+"'s "+
	                  "assignment " + assignment.getNumber()+
	                  " has gone from " +oldStatus+"to"+assignment.getStatus()+" " + link;
		      
		        Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
		        prop.put("mail.smtp.port", "587");
		        prop.put("mail.smtp.auth", "true");
		        prop.put("mail.smtp.starttls.enable", "true"); //TLS
		        
		        Session session = Session.getInstance(prop,
		                new javax.mail.Authenticator() {
		                    protected PasswordAuthentication getPasswordAuthentication() {
		                        return new PasswordAuthentication(username, password);
		                    }
		                });

		        try {

		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(username));
		            message.setRecipients(
		                    Message.RecipientType.TO,
		                    InternetAddress.parse(codeReviewer)
		            );
		            message.setSubject("Assignment Status Update");
		            message.setText(emailMessage);

		            Transport.send(message);

		            System.out.println("Done");

		        } catch (MessagingException e) {
		            e.printStackTrace();
		        }
		    }
	  
}
