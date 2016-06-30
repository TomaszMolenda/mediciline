package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Wrona data input")
public class WrongDataInputException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(WrongDataInputException.class);
	
    public WrongDataInputException(HttpServletRequest request) {
		
	}
    
    public WrongDataInputException() {
		
	}
}
