package pl.tomo.controller.exception;

public class UserValidationException extends RuntimeException {
	
	String msg;
	
    public UserValidationException(String msg) {
    	this.msg = msg;
	}
    
    public String getMessage() {
    	return msg;
    }
    
}
