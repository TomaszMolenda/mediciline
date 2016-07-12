package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such element")
public class NoSuchElementException extends RuntimeException {
	
    public NoSuchElementException(HttpServletRequest request) {
	}
}
