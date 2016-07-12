package pl.tomo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.tomo.entity.form.UserChangePassword;

@Component
public class ChangePasswordValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz	) {
		
		return UserChangePassword.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserChangePassword userChangePassword = (UserChangePassword) target;
		String password = userChangePassword.getPassword();
		String confirmPassword = userChangePassword.getConfirmPassword();
		if(!(password.equals(confirmPassword)))
		{
			errors.rejectValue("confirmPassword", "error.confirmPassword", "Hasła nie pasują do siebie");
		}
		if(password.length() < 5)
		{
			errors.rejectValue("password", "error.password", "Hasło jest za krótkie");
		}
		if(confirmPassword.length() < 5)
		{
			errors.rejectValue("confirmPassword", "error.confirmPassword", "Hasło jest za krótkie");
		}
	}

}
