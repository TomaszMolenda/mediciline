package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/list")
	public ModelAndView showPatients(Principal principal)
	{ 
		String name = principal.getName();
		List<Patient> patients = patientService.getAllByUser(name);
		ModelAndView modelAndView = new ModelAndView("patients");
		modelAndView.addObject("patients", patients);
		modelAndView.addObject("patient", new Patient());
		return modelAndView;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(@ModelAttribute Patient patient, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("redirect:/patients/list.html");
		String userName = principal.getName();
		User user = userService.findByName(userName);
		patient.setUser(user);
		try {
			patient.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patient.getBirthdayString()));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		patientService.save(patient);
		
		return modelAndView;
	}
	
	
}
