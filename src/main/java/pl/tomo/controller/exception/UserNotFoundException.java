package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
public class UserNotFoundException extends RuntimeException {
	
    public UserNotFoundException(HttpServletRequest request) {
	}
}
