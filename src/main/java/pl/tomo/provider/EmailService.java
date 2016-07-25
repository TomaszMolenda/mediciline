package pl.tomo.provider;

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

	public void sendRegistrationEmail(String address, String uniqueID) {
		
		User user = userService.findByUniqueID(uniqueID);
		
		String subject = "Rejestracja w serwisie Mediciline";
		String link = "http://212.244.79.82:8085/confirm/" + uniqueID;
		String text = "Witaj " + user.getName() + "!,<br><br>Kliknij w poniższy link aby aktywować konto<br><br>"
				+ "<a href=\"" + link + "\">Aktywacja konta</a>";
		sendEmail(subject, text, address);
	
		logger.info("Send registration email to: " + address);


		
	}

	public void sendReminderEmail(Dosage dosage, DiseaseMedicament diseaseMedicament) {
		User user = dosage.getUser();
		Medicament medicament = diseaseMedicament.getMedicament();

		String subject = "[Przypomnienie] - Dawka leku " + medicament.getName() + " - " + 
				dosage.getDose() + " " + dosage.getUnit();
		String text = "Witaj " + user.getName() + "\n";
		text += "Choroba: " + diseaseMedicament.getDisease().getName() + "\n";
		text += "Lek: " + medicament.getName() + "\n";
		text += "Dawka: " + dosage.getDose() + " " + dosage.getUnit();

		sendEmail(subject, text, user.getEmail());

		logger.info("Send reminder email to: " + user.getEmail());

		
	}
	
	private void sendEmail(String subject, String text, String address) {
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
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}
