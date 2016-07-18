package pl.tomo.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.controller.exception.UserValidationException;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.service.UserService;
import pl.tomo.validator.ServiceValidation;

@RestController
@RequestMapping(value = "/api")
public class RestUserController {
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServiceValidation serviceValidation; 
	
	@RequestMapping(value = "/register", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<?> register(@RequestBody User user, HttpServletRequest request) {
		User savedUser = null;
		try {
			savedUser = userService.save(user);
		} catch (ConstraintViolationException e) {
			String jsonError = serviceValidation.createJson(e);
			return new ResponseEntity<String>(jsonError, HttpStatus.BAD_REQUEST);
		} catch (UserValidationException e) {
			String jsonError = e.getMessage();
			return new ResponseEntity<String>(jsonError, HttpStatus.BAD_REQUEST);
		}
		User returnValue = json.use(JsonView.with(savedUser).onClass(User.class, Match.match()
				.exclude("medicaments")
				.exclude("diseases")
				.exclude("patients")
				.exclude("roles")
				.exclude("files")
				.exclude("dosages"))).returnValue();		
		return new ResponseEntity<User>(returnValue, HttpStatus.OK);
	}
	
}
