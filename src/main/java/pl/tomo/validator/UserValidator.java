package pl.tomo.validator;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.validation.ConstraintViolation;

import org.apache.log4j.net.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.tomo.controller.exception.UserValidationException;
import pl.tomo.entity.User;
import pl.tomo.service.UserService;

@Component
public class UserValidator {
	
	@Autowired
	private UserService userService;
	
	

	public void validate(User user) throws UserValidationException {
		
		String json = "{\"errors\":[";
		boolean hasError = false;
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			json += "{\"path\":\"password\", \"message\":\"Hasła nie pasują do siebie\"},";
			hasError = true;
		}
		if(!user.getEmail().equals(user.getConfirmEmail())) {
			json += "{\"path\":\"email\", \"message\":\"Emaile nie pasują do siebie\"},";
			hasError = true;
		}
		List<String> emails = userService.findAllEmail();
		if(emails.contains(user.getEmail()))
		{
			json += "{\"path\":\"isemail\", \"message\":\"Email istnieje w bazie\"},";
			hasError = true;
		}
		
		List<String> names = userService.findAllName();
		if(names.contains(user.getName()))
		{
			json += "{\"path\":\"isname\", \"message\":\"Nazwa istnieje w bazie\"},";
			hasError = true;
		}
		json = json.substring(0, json.length() - 1);
		json += "]}";
		if(hasError)
			throw new UserValidationException(json);
		
	}
	


}
