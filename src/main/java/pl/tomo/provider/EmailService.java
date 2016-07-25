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

import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.service.UserService;

@Service
public class EmailService{
	
	private Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	private Email email;
	
	@Autowired
	private UserService userService;

	public void sendEmail(String sendTo, String uniqueID) {
		
		User user = userService.findByUniqueID(uniqueID);
		
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
			String link = "http://212.244.79.82:8085/confirm/" + uniqueID;
			message.setText("Witaj " + user.getName() + "!,<br><br>Kliknij w poniższy link aby aktywować konto<br><br>"
				+ "<a href=\"" + link + "\">Aktywacja konta</a>");
			
			Transport.send(message);

			logger.info("Send registration email to: " + sendTo);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void sendReminderEmail(Dosage dosage, DiseaseMedicament diseaseMedicament) {
		User user = dosage.getUser();
		Medicament medicament = diseaseMedicament.getMedicament();
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
				InternetAddress.parse(user.getEmail()));
			String subject = "[Przypomnienie] - Dawka leku " + medicament.getName() + " - " + 
				dosage.getDose() + " " + dosage.getUnit();
			message.setSubject(subject);
			String text = "Witaj " + user.getName() + "\n";
			text += "Choroba: " + diseaseMedicament.getDisease().getName() + "\n";
			text += "Lek: " + medicament.getName() + "\n";
			text += "Dawka: " + dosage.getDose() + " " + dosage.getUnit();
			message.setText(text);
			
			Transport.send(message);

			logger.info("Send reminder email to: " + user.getEmail());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
}
