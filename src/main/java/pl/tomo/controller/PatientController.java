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
		ModelAndView modelAndView = new ModelAndView("patients");
		logger.info("user : " + principal.getName() + " open /patients/list");
		return modelAndView;
	}
	
	
}
