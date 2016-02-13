package pl.tomo.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.provider.Email;

@Service
public class EmailService{

	@Autowired
	private Email email;

	public void sendEmail(String sendTo, String uniqueID) {
		
		Session session = Session.getInstance(email.getProps(),
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email.getUser(), email.getPassword());
					}
				  });
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("tomasz.molenda.autoguard.yt@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sendTo));
			message.setSubject("Rejestracja w serwisie Mediciline");
			message.setText("Witaj!,"
				+ "http://localhost:8080/confirm/" + uniqueID + ".html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
}
