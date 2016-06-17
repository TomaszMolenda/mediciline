package pl.tomo.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No save file")
public class NoSaveFileException extends RuntimeException {
	
	private Logger logger = Logger.getLogger(NoSaveFileException.class);
	
    public NoSaveFileException(HttpServletRequest request) {
		logger.info("try save file (no success), from ip " + request.getRemoteAddr() + " (No save file)");
	}
}
