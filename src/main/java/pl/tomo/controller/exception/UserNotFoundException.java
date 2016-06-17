package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
public class UserNotFoundException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(UserNotFoundException.class);
	
    public UserNotFoundException(HttpServletRequest request) {
		logger.info("No access from ip " + request.getRemoteAddr() + " (No such user)");
	}
}
