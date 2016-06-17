package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such element")
public class NoSuchElementException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(NoSuchElementException.class);
	
    public NoSuchElementException(HttpServletRequest request) {
		logger.info("No access from ip " + request.getRemoteAddr() + " (No such element)");
	}
}
