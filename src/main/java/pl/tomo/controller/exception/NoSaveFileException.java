package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No save file")
public class NoSaveFileException extends RuntimeException {

	
    public NoSaveFileException(HttpServletRequest request) {
	}
}
