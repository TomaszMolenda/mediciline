package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Patient has a disease")
public class DeleteHasChildException extends RuntimeException {
	
    public DeleteHasChildException(HttpServletRequest request) {
	}
}
