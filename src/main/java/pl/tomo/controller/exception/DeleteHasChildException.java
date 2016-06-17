package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Patient has a disease")
public class DeleteHasChildException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(DeleteHasChildException.class);
	
    public DeleteHasChildException(HttpServletRequest request) {
		logger.info("User try delete patient with disease from ip " + request.getRemoteAddr());
	}
}
