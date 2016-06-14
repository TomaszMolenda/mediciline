package pl.tomo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePassword {
	
	private String password;
	private String confirmPassword;
	
}
