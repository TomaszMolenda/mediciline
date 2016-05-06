package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/patients")
public class PatientController {
	
	private Logger logger = Logger.getLogger(PatientController.class);
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/list")
	public ModelAndView showPatients(Principal principal)
	{ 
		List<Patient> patients = patientService.getAllByUser(principal.getName());
		ModelAndView modelAndView = new ModelAndView("patients");
		modelAndView.addObject("patients", patients);
		modelAndView.addObject("patient", new Patient());
		logger.info("user : " + principal.getName() + " open /patients/list");
		return modelAndView;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(@ModelAttribute Patient patient, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("redirect:/patients/list.html");
		User user = userService.findByName(principal.getName());
		patient.setUser(user);
		try {
			patient.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patient.getBirthdayString()));
		} catch (ParseException e) {
			logger.info("user : " + principal.getName() + " try parse birthday - no success");
		} 
		patientService.save(patient);
		logger.info("user : " + principal.getName() + " add patient: " + patient.getId());
		return modelAndView;
	}
	
	
}
