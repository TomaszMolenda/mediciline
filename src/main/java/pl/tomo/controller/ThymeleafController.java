package pl.tomo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Medicament;
import pl.tomo.service.MedicamentService;

@Controller
@RequestMapping(value = "/thymleaf")
public class ThymeleafController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@RequestMapping(value = "/test")
	public ModelAndView test(HttpServletRequest request) {
		List<Medicament> medicaments = medicamentService.findAllActive(request);
		ModelAndView modelAndView = new ModelAndView("medicaments");
		modelAndView.addObject("medicaments", medicaments);
		return modelAndView;
	}

}
