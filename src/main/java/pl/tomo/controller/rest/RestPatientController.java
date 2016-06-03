package pl.tomo.controller.rest;

import java.security.acl.NotOwnerException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class RestPatientController {
	
	private Logger logger = Logger.getLogger(RestPatientController.class);
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private PatientService patientService; 
	
	@Autowired
	private UserService userService;
	

	
	//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
    class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(HttpServletRequest request) {
			logger.info("No access from ip " + request.getRemoteAddr() + " (No such user)");
		}
    }
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such element")
    class NoSuchElementException extends RuntimeException {
        public NoSuchElementException(HttpServletRequest request) {
			logger.info("No access from ip " + request.getRemoteAddr() + " (No such element)");
		}
    }
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Parse exception")
    class ParseException extends RuntimeException {
        public ParseException(HttpServletRequest request) {
			logger.info("Error " + request.getRemoteAddr() + " (Parse exception)");
		}
    }
	
	@RequestMapping(value = "/patients", method=RequestMethod.GET)
	@ResponseBody
	public void getAllPatients(HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
		if(user == null) {
			throw new UserNotFoundException(request);
		}
		List<Patient> patients = patientService.getAllByUser(user.getName());
		json.use(JsonView.with(patients).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")));
		
	}
	
	@RequestMapping(value = "/patient", method=RequestMethod.POST)
	@ResponseBody
	public String getPatient(@RequestBody Patient patient, HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
		if(user == null) {
			throw new UserNotFoundException(request);
		}
		patient.setUser(user);
		if(patient.getName() == "") {
			throw new NoSuchElementException(request);
		}
		try {
			patientService.save(patient);
		} catch (java.text.ParseException e) {
			logger.info("Connot convert date from " + patient.getBirthdayString());
			throw new ParseException(request);
		}
		return "ok";
	}
	
	@RequestMapping(value = "/patient/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void getPatient(@PathVariable("id") int id, HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
		if(user == null) {
			throw new UserNotFoundException(request);
		}

		Patient patient = patientService.getById(id);
		json.use(JsonView.with(patient).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")));
	}
	
	@RequestMapping(value = "/patient/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deletePatient(@PathVariable("id") int id, HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
		if(user == null) {
			throw new UserNotFoundException(request);
		}

		patientService.delete(id);
		return "ok";
	}
	
	
	
	private String getAuthCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("AUTH"))
				return cookie.getValue();
		}
		return null;
	}
	

}
