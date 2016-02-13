package pl.tomo.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.service.RoleService;
import pl.tomo.service.UserService;



public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName().trim().toLowerCase();
		String password = authentication.getCredentials().toString().trim();
		
		
		User user = userService.findByEmail(email);
		List<Role> roles = roleService.findByUser(user);
		

		
		if (user != null && email.equals(user.getEmail()) && password.equals(user.getPassword()) && user.isActive()==true) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			for (Role role : roles) {
				grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
			}
			
			Authentication auth = new UsernamePasswordAuthenticationToken(user.getName(), password, grantedAuths);
			return auth;
		} else {

			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
