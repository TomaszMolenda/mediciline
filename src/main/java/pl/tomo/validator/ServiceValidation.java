package pl.tomo.validator;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

@Service
public class ServiceValidation {

	public String createJson(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		
		String json = "{\"errors\":[";
		for(Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator(); iterator.hasNext();) {
			ConstraintViolation<?> next = iterator.next();
			
			json += "{\"path\":\"" + next.getPropertyPath() + "\", ";
			json += "\"message\":\"" + next.getMessage() + "\"}";
			if(iterator.hasNext()) json += ",";
		}
		json += "]}";
		return json;
	}
	
	

}
