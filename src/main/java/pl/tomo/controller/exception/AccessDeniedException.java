package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Access denied")
public class AccessDeniedException extends RuntimeException {
	
	
    public AccessDeniedException(HttpServletRequest request) {
	}
    
    public AccessDeniedException() {
		
	}
}
