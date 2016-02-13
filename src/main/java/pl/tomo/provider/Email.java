package pl.tomo.provider;

import java.util.Properties;

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendUniqueID() {
		return sendUniqueID;
	}

	public void setSendUniqueID(String sendUniqueID) {
		this.sendUniqueID = sendUniqueID;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	
	
	

}
