package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Get additional information fail")
public class AdditionalInformationException extends RuntimeException {
	
	
	public AdditionalInformationException(HttpServletRequest request) {
	}

}
