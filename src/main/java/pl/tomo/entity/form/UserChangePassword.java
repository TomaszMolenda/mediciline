package pl.tomo.entity.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserChangePassword {
	
	private String password;
	private String confirmPassword;
	
}
