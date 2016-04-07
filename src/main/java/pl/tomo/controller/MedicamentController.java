package pl.tomo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Medicament;
import pl.tomo.service.MedicamentService;

@Controller
@RequestMapping(value = "/medicament")
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal)
	{
		ModelAndView modelAndView = new ModelAndView("medicaments");
		String name = principal.getName();
		Medicament medicament = new Medicament();
		List<Medicament> medicaments = medicamentService.findByUser(name);
		modelAndView.addObject("medicaments", medicaments);
		modelAndView.addObject("medicament", medicament);
		return modelAndView;
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView change(@Valid @ModelAttribute("medicament") Medicament medicament, BindingResult result, Principal principal)
	{
		if(result.hasErrors())
		{
			System.out.println(result.toString());
			return new ModelAndView("medicamentList");
		}
		
		String name = principal.getName();
		medicamentService.save(medicament, name);
		return new ModelAndView("redirect:/medicament/list.html");
	}
	
	@RequestMapping(value = "/remove/{id}")
	public ModelAndView remove(@PathVariable int id, Principal principal)
	{
		String name = principal.getName();
		Medicament medicament = medicamentService.findByIdWithUser(id);
		if(medicament.getUser().getName().equals(name))
		{
			medicamentService.delete(id);
			return new ModelAndView("redirect:/medicament/list.html");
		}

		return new ModelAndView("redirect:/no-access.html");
	}
	
	
	


}
