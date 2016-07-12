package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Wrona data input")
public class WrongDataInputException extends RuntimeException {
	
	
    public WrongDataInputException(HttpServletRequest request) {
		
	}
    
    public WrongDataInputException() {
		
	}
}
