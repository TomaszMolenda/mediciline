package pl.tomo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jcabi.aspects.Loggable;

import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/patients")
@Loggable
public class PatientController {
	
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping
	public ModelAndView showPatients(HttpServletRequest request)
	{ 
		ModelAndView modelAndView = new ModelAndView("patients/patients");
		return modelAndView;
	}
	
	
}
