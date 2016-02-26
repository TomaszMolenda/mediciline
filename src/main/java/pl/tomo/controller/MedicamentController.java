package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import pl.tomo.entity.MedicamentDb;
import pl.tomo.entity.User;
import pl.tomo.service.MedicamentDbService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/medicament")
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicamentDbService medicamentDbService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal)
	{
		ModelAndView mav = new ModelAndView("medicamentList");
		String name = principal.getName();
		User user = userService.findByName(name);
		Medicament medicament = new Medicament();
		List<Medicament> medicaments = medicamentService.findByUser(user);
		mav.addObject("medicaments", medicaments);
		mav.addObject("medicament", medicament);
		
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSumit(@Valid @ModelAttribute("medicament") Medicament medicament, BindingResult result, Principal principal)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("medicamentList");
		}
		String name = principal.getName();
		try {
			String date = medicament.getDateExpirationYearMonth().getYear() + "-" + medicament.getDateExpirationYearMonth().getMonthId() + "-01";
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user = userService.findByName(name);
		MedicamentDb medicamentDb = medicamentDbService.findOne(medicament.getidMedicamentDb());
		medicament.setMedicamentDb(medicamentDb);
		medicament.setUser(user);
		medicamentService.save(medicament);
		return new ModelAndView("redirect:/medicament/list.html");
	}
	
	@RequestMapping(value = "/remove/{id}")
	public ModelAndView remove(@PathVariable int id, Principal principal)
	{
		String name = principal.getName();

		Medicament medicament = medicamentService.findById(id);
		
		if(medicament.getUser().getName().equals(name))
		{
			medicamentService.delete(id);
			return new ModelAndView("redirect:/medicament/list.html");
		}

		return new ModelAndView("redirect:/no-access.html");
	}
		
	@RequestMapping(value="/edit")
	public ModelAndView editSubmit(Medicament medicament, Principal principal) {
		
		ModelAndView mav = new ModelAndView("redirect:/medicament/list.html");
		Medicament medicamentEdited = medicamentService.findById(medicament.getId());
		
		try {
			medicamentEdited.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String name = principal.getName();
		if(medicamentEdited.getUser().getName().equals(name))
		{
			medicamentService.save(medicamentEdited);
		}
		else return new ModelAndView("redirect:/no-access.html");

		return mav;
	}
	
	
	
	


}
