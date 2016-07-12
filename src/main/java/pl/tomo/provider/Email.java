package pl.tomo.provider;

import java.util.Properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Email {
	
	private String user;
	
	private String password;
	
	private String sendTo;
	
	private String sendUniqueID;
	
	private Properties props;

	public Email(String user, String password) {
		this.user = user;
		this.password = password;
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}
}
