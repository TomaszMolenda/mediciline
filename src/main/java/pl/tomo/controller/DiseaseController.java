package pl.tomo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Patient;
import pl.tomo.entity.form.PatientForm;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.PatientService;

@Controller
@RequestMapping(value = "/diseases")
@SessionAttributes(value = "patient")
public class DiseaseController {
	
		
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap, @RequestParam("list") String list) {
		ModelAndView modelAndView = new ModelAndView("diseases");
		PatientForm patientForm = diseaseService.getPatientForm(request);
		modelAndView.addObject("patientForm", patientForm);
		Patient patient = (Patient) modelMap.get("patient");
		if(patient != null) {
			List<Disease> diseases = diseaseService.findAllActive(patient, list);
			modelAndView.addObject("diseases", diseases);
			modelAndView.addObject("disease", new Disease());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public ModelAndView setSessionPatient(HttpServletRequest request, @ModelAttribute("patientForm") PatientForm patientForm) {
		ModelAndView mav = new ModelAndView();
		int id = patientForm.getId();
		Patient patient = patientService.getById(id);
		mav.addObject("patient", patient);
		mav.setViewName("redirect:/diseases" + Utills.makeUrlQueryByPrevious(request));
		return mav;
	}
	
	@RequestMapping(value = "/patient/change", method = RequestMethod.GET)
	public ModelAndView deleteSessionPatient(HttpServletRequest request, SessionStatus sessionStatus) {
		ModelAndView mav = new ModelAndView("redirect:/diseases" + Utills.makeUrlQueryByPrevious(request));
		sessionStatus.setComplete();
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSubmit(HttpServletRequest request, @ModelAttribute("disease") Disease disease) {
		Patient patient = (Patient) request.getSession().getAttribute("patient");
		diseaseService.save(disease, patient);
		return new ModelAndView("redirect:/diseases" + Utills.makeUrlQueryByPrevious(request));
	}
	
	
	@RequestMapping(value = "/archive/{id}", method = RequestMethod.GET)
	public ModelAndView archive(HttpServletRequest request, @PathVariable("id") int id, @RequestParam("date") long date) {
		ModelAndView modelAndView = new ModelAndView("redirect:/diseases" + Utills.makeUrlQueryByPrevious(request));
		diseaseService.archive(id, date, request);
		return modelAndView;
	}
	
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public ModelAndView getInfo(HttpServletRequest request, @PathVariable("id") int id) {
		List<Disease> diseases = diseaseService.findByIdTest(id);
//		System.out.println(disease.getUser().getMedicaments().size());
		System.out.println(diseases.get(0).getUser().getMedicaments().size());
		ModelAndView modelAndView = new ModelAndView("medicaments/info");
		return modelAndView;
	}
	
	

}
