package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentDb;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
public class DiseaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@RequestMapping(value = "/diseases", method = RequestMethod.GET)
	public String diseases(Model model, Principal principal)
	{
		String name = principal.getName();
		User user = userService.findByName(name);
		List<Disease> diseases = diseaseService.findByUser(user);
		List<Medicament> medicaments = medicamentService.findByUser(user);
		model.addAttribute("diseases", diseases);
		model.addAttribute("medicaments", medicaments);
		return "diseases";
	}
	
	@RequestMapping(value = "/add-disease", method = RequestMethod.GET)
	public String diseaseAdd(Model model)
	{
		model.addAttribute("disease", new Disease());
		return "add-disease";
	}
	
	@RequestMapping(value = "/add-disease", method = RequestMethod.POST)
	public String doDiseaseAdd(@ModelAttribute("disease") Disease disease, Principal principal)
	{
		String name = principal.getName();
		try {
			disease.setStart(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStartString()));
			disease.setStop(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStopString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User user = userService.findByName(name);

		disease.setUser(user);
		diseaseService.save(disease);
		return "redirect:/medicaments.html";
	}
	
	@RequestMapping(value = "/add-medicament-to-disease-{id}", method = RequestMethod.GET)
	public ModelAndView  addMedicamentToDisease(@PathVariable int id, Principal principal)
	{
		
		String name = principal.getName();
		User user = userService.findByName(name);
		ModelAndView mav = new ModelAndView("disease-medicaments", "medicament", medicamentService.findByUser(user));
		//model.addAttribute("medicaments", medicamentService.findByUser(user));
		return mav;
		//return "disease-medicaments";
	}
	
	@RequestMapping(value = "/add-medicament-to-disease", method = RequestMethod.POST)
	public String doAddMedicamentToDisease(@ModelAttribute("medicaments") Medicament medicaments, Principal principal)
	{
		String name = principal.getName();
		
		
		User user = userService.findByName(name);
		
		
		return "redirect:/medicaments.html";
	}

	
	

}
