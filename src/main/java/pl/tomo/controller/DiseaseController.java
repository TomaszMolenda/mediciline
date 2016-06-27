package pl.tomo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Disease;
import pl.tomo.service.DiseaseService;

@Controller
@RequestMapping(value = "/diseases")
public class DiseaseController {
	
	@Autowired
	private DiseaseService diseaseService;
	
	@RequestMapping
	public ModelAndView list(HttpServletRequest request) {
		List<Disease> diseases = diseaseService.findByRequest(request);
		ModelAndView modelAndView = new ModelAndView("diseases");
		modelAndView.addObject("diseases", diseases);
		modelAndView.addObject("disease", new Disease());
		return modelAndView;
	}

}
