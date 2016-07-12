package pl.tomo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jcabi.aspects.Loggable;

import pl.tomo.entity.Medicament;
import pl.tomo.service.MedicamentService;

@Controller
@RequestMapping(value = "/medicaments")
@Loggable
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@RequestMapping
	public ModelAndView list(HttpServletRequest request) {
		List<Medicament> medicaments = medicamentService.findAllActive(request);
		ModelAndView modelAndView = new ModelAndView("medicaments/medicaments");
		modelAndView.addObject("medicaments", medicaments);
		modelAndView.addObject("medicament", new Medicament());
		return modelAndView;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, @ModelAttribute("medicament") Medicament medicament) {
		ModelAndView modelAndView = new ModelAndView("redirect:/medicaments");
		medicamentService.save(medicament, request);
		return modelAndView;
	}
	
	@RequestMapping(value = "archive/{id}", method = RequestMethod.GET)
	public ModelAndView archive(HttpServletRequest request, @PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/medicaments");
		medicamentService.archive(id, request);
		return modelAndView;
	}

}
