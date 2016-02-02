package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentDb;
import pl.tomo.entity.User;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.MedicamentDbService;
import pl.tomo.service.UserService;

@Controller
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicamentDbService medicamentDbService;
	
	@RequestMapping(value = "/medicaments", method = RequestMethod.GET)
	public String index(Model model, Principal principal)
	{
		String name = principal.getName();
		User user = userService.findByName(name);
		model.addAttribute("medicaments", medicamentService.findByUser(user));
		return "medicaments";
	}
	
	@RequestMapping(value = "/add-medicament", method = RequestMethod.GET)
	public String medicamentAdd(Model model)
	{
		model.addAttribute("medicament", new Medicament());
		return "add-medicament";
	}
	
	@RequestMapping(value = "/add-medicament", method = RequestMethod.POST)
	public String doMedicamentAdd(@ModelAttribute("medicament") Medicament medicament, Principal principal)
	{
		String name = principal.getName();
		try {
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User user = userService.findByName(name);
		
		MedicamentDb medicamentDb = medicamentDbService.findOne(medicament.getLiczba());
		medicament.setMedicamentDb(medicamentDb);
		medicament.setUser(user);
		medicamentService.save(medicament);
		return "redirect:/medicaments.html";
	}

	
	
	
	@RequestMapping(value = "/medicament/remove/{id}")
	public String lekRemove(@PathVariable int id, Principal principal)
	{
		String name = principal.getName();
		User user = userService.findByName(name);
		Medicament medicament = medicamentService.findById(id);
		if(medicament.getUser().getName().equals(user.getName()))
		{
			medicamentService.delete(id);
			return "redirect:/index.html";
		}

		return "redirect:/no-access.html";
	}
	
	@RequestMapping(value="/edit-medicament-{id}", method = RequestMethod.GET)
	public ModelAndView user(@PathVariable int id){
		Medicament medicament = medicamentService.findById(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		medicament.setDateStringExpiration(sdf.format(medicament.getDateExpiration()));
		return new ModelAndView("edit-medicament","command", medicament);
	}
	
	@RequestMapping(value="/edited-medicament", method = RequestMethod.POST)
	public String createUser(@ModelAttribute("x") Medicament medicament) {
		try {
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		medicamentService.update(medicament.getId(), medicament.getDateExpiration());
		return "redirect:medicaments.html";
	}
	
//	@RequestMapping(value = "/medicament-edit-{id}", method = RequestMethod.GET)
//	public String medicamentEdit(@PathVariable int id, Model model)
//	{
//		Medicament lek = medicamentService.findById(id);
//		model.addAttribute("medicament", lek);	
//		return "edit-medicament";
//	}
//	
//	@RequestMapping(value = "/medicament-edit-{id}", method = RequestMethod.POST)
//	public String doMedicamentEdit(@ModelAttribute("medicament") Medicament medicament, @PathVariable int id)
//	{
//		try {
//			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		medicamentService.update(medicament);
//		return "redirect:/medicaments.html";
//	}

}
