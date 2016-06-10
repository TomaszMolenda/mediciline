package pl.tomo.controller.rest;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;
import pl.tomo.validator.ServiceValidation;

@Controller
@RequestMapping(value = "/api")
public class RestPatientController {
	
	private Logger logger = Logger.getLogger(RestPatientController.class);
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private PatientService patientService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServiceValidation serviceValidation; 
	

	
	//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
    class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(HttpServletRequest request) {
			logger.info("No access from ip " + request.getRemoteAddr() + " (No such user)");
		}
    }
	
	@RequestMapping(value = "/patients", method=RequestMethod.GET)
	@ResponseBody
	public void getAllPatients(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		List<Patient> patients = patientService.getAllByUser(user.getName());
		json.use(JsonView.with(patients).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")));
		
	}
	
	@RequestMapping(value = "/patient", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getPatient(@RequestBody Patient patient, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		patient.setUser(user);
		try {
			patientService.save(patient);
		} catch (ConstraintViolationException e) {
			String json = serviceValidation.createJson(e);
			return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/patient/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void getPatient(@PathVariable("id") int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		Patient patient = patientService.getById(id);
		json.use(JsonView.with(patient).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")));
	}
	
	@RequestMapping(value = "/patient/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deletePatient(@PathVariable("id") int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		patientService.delete(id);
		return "ok";
	}
	

}
