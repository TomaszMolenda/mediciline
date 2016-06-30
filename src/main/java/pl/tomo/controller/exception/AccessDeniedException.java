package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Access denied")
public class AccessDeniedException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(AccessDeniedException.class);
	
    public AccessDeniedException(HttpServletRequest request) {
		logger.info("Access denied from ip " + request.getRemoteAddr());
	}
    
    public AccessDeniedException() {
		
	}
}
