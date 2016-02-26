package pl.tomo.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.tomo.entity.User;
import pl.tomo.service.UserService;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class clazz	) {
		
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Email is required.");
		User user = (User) target;
		if(!(user.getPassword().equals(user.getConfirmPassword())))
		{
			
			errors.rejectValue("confirmPassword", "error.confirmPassword", "Hasła nie pasują do siebie");
		}
		
		List<String> emails = userService.findAllEmail();

		if(emails.contains(user.getEmail()))
		{
			errors.rejectValue("email", "error.email", "Email istnieje w bazie");
		}
		
		List<String> names = userService.findAllName();
		if(names.contains(user.getName()))
		{
			errors.rejectValue("name", "error.name", "Nazwa istnieje w bazie");
		}
		
	}

}
