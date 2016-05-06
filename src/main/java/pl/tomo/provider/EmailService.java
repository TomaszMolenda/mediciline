package pl.tomo.provider;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
	
	private Logger logger = Logger.getLogger(EmailService.class);

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
				+ "http://212.244.79.82:8080/confirm/" + uniqueID + ".html");

			Transport.send(message);

			logger.info("Send registration email to: " + sendTo);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
}
