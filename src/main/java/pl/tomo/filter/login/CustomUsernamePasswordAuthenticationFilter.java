package pl.tomo.filter.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pl.tomo.controller.PatientController;
import pl.tomo.entity.User;
import pl.tomo.service.UserService;

//http://mrather.blogspot.com/2010/02/extending-usernamepasswordauthenticatio.html
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private Logger logger = Logger.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
	
	@Autowired
	private UserService userService;

@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	
	Cookie[] cookies = request.getCookies();
	String jSESSIONID = null;
	for (Cookie cookie : cookies) {
		if(cookie.getName().equals("JSESSIONID")) {
			jSESSIONID = cookie.getValue();
			break;
		}
	}

	String name = authResult.getName();
	if(name != null & jSESSIONID != null) {
		User user = userService.findByName(name);
		user.setJSESSIONID(jSESSIONID);
		Cookie cookie = new Cookie("AUTH", user.getAuth());
		cookie.setPath("/");
		response.addCookie(cookie);
		userService.save(user);
		logger.info("User " + name + " logged from ip " + request.getRemoteAddr());
	}
	
	super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		logger.info("Someone try loggin from ip " + request.getRemoteAddr());
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	

}
