package pl.tomo.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.service.RoleService;
import pl.tomo.service.UserService;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName().trim().toLowerCase();
		String password = authentication.getCredentials().toString().trim();
		User user = userService.findByEmail(email);
		List<Role> roles = roleService.findByUser(user);
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		Authentication auth = null;
		
		if(email.equals("demo") & password.equals("demo")) {
			System.out.println("jest demo");
			List<Integer> usersDemoNo = userService.findAllDemoNo();
			int max = 0;
			if(!usersDemoNo.isEmpty()) max = Collections.max(usersDemoNo).intValue();
			int userNo = 1;
			for(int i = 1; i <= max; i++) {
				if(!usersDemoNo.contains(i)) {
					userNo = i;
					break;
				}
				else userNo = max + 1;
			}
			User demoUser = new User();
			demoUser.setName("demo" + Integer.toString(userNo));
			demoUser.setEmail("demo@demo.demo");
			demoUser.setActive(false);
			demoUser.setPassword("demo");
			demoUser.setDemoNo(userNo);
			Role demoRole = roleService.findByName("ROLE_USER");
			demoUser.getRoles().add(demoRole);
			userService.save(demoUser);
			
			grantedAuths.add(new SimpleGrantedAuthority(demoRole.getName()));
			auth = new UsernamePasswordAuthenticationToken(demoUser.getName(), demoUser.getPassword(), grantedAuths);
			

		}
		
		if (user != null && email.equals(user.getEmail()) && password.equals(user.getPassword()) && user.isActive()==true) {
			for (Role role : roles) {
				grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
			}
			auth = new UsernamePasswordAuthenticationToken(user.getName(), password, grantedAuths);

		}
		if(auth != null) {
			logger.info("User: " + email + " logged from IP: " + request.getRemoteAddr());
		}
		else {
			logger.info("UNAUTORIZED! User: " + email + " logged from IP: " + request.getRemoteAddr());
		}
		
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
