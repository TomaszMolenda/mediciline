package pl.tomo.provider;

import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;

@Service
public class EmailService{
	
	private Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	private Email email;

	
	public void sendEmail(String subject, String text, String address) {
		Session session = Session.getInstance(email.getProps(),
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email.getUser(), email.getPassword());
					}
				  });
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("tomasz.molenda.autoguard.yt@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(address));
			subject += " [" + UUID.randomUUID().toString().substring(0, 4) + "]";
			message.setSubject(subject);
			message.setContent(text, "text/html; charset=UTF-8");
			Transport.send(message);
		} catch (AddressException e) {
			throw new AccessDeniedException();
		} catch (MessagingException e) {
			throw new AccessDeniedException();
		}
		
		logger.info("Send email to: " + address);
		
	}
	
	
	
}
