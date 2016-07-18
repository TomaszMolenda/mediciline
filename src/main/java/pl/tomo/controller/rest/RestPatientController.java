package pl.tomo.controller.rest;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.controller.exception.DeleteHasChildException;
import pl.tomo.controller.exception.UserNotFoundException;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;
import pl.tomo.validator.ServiceValidation;

@RestController
@RequestMapping(value = "/api")
public class RestPatientController {
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private PatientService patientService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServiceValidation serviceValidation; 
	
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
	
	@RequestMapping(value = "/patient", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getPatient(@RequestBody Patient patient, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		patient.setUser(user);
		Patient savedPatient = null;
		try {
			savedPatient = patientService.save(patient);
		} catch (ConstraintViolationException e) {
			String json = serviceValidation.createJson(e);
			return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
		}
		Patient returnValue = json.use(JsonView.with(savedPatient).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")
				.exclude("birthday"))).returnValue();
		return new ResponseEntity<Patient>(returnValue, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/patient/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void getPatient(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
		Patient patient = getPatient(id, request);
		json.use(JsonView.with(patient).onClass(Patient.class, Match.match()
				.exclude("user")
				.exclude("diseases")));
	}
	
	@RequestMapping(value = "/patient/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deletePatient(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
		Patient patient = getPatient(id, request);
		try {
			patientService.delete(patient);
		} catch (EmptyResultDataAccessException e) {
			throw new AccessDeniedException(request);
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DeleteHasChildException(request);
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private Patient getPatient(int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user == null) throw new UserNotFoundException(request);
		Patient patient = null;
		try {
			patient = patientService.getById(id);
		} catch (NoResultException e) {
			throw new AccessDeniedException(request);
		}
		if(patient!=null & !patient.getUser().equals(user)) throw new AccessDeniedException(request);
		return patient;
	}
	

}
