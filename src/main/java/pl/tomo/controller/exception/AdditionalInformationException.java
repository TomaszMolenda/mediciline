package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Get additional information fail")
public class AdditionalInformationException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(AdditionalInformationException.class);
	
	public AdditionalInformationException(HttpServletRequest request) {
		logger.info("No access from ip " + request.getRemoteAddr() + " (Get additional information fail)");
	}

}
