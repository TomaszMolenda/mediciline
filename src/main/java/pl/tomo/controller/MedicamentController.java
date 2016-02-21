package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		mav.addObject("medicaments", medicamentService.findByUser(user));
		mav.addObject("medicament", medicament);
		
		return mav;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add()
	{
		ModelAndView mav = new ModelAndView("medicamentAdd");
		mav.addObject("medicament", new Medicament());
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSumit(@Valid @ModelAttribute("medicament") Medicament medicament, BindingResult result, Principal principal)
	{
		if(result.hasErrors())
		{
			return new ModelAndView("medicamentAdd");
		}
		String name = principal.getName();
		try {
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));

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
		User user = userService.findByName(name);
		Medicament medicament = medicamentService.findById(id);
		if(medicament.getUser().getName().equals(user.getName()))
		{
			medicamentService.delete(id);
			return new ModelAndView("redirect:/medicament/list.html");
		}

		return new ModelAndView("redirect:/no-access.html");
	}
	
	@RequestMapping(value="/edit/{id}")
	public ModelAndView edit(@PathVariable int id){
		ModelAndView mav = new ModelAndView("medicamentEdit");
		Medicament medicament = medicamentService.findById(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		medicament.setDateStringExpiration(sdf.format(medicament.getDateExpiration()));
		mav.addObject("medicament", medicament);
		return mav;
	}
	
	@RequestMapping(value="/do")
	public ModelAndView editSubmit(Medicament medicament) {
		ModelAndView mav = new ModelAndView("redirect:/medicament/list.html");
		Medicament medicamentEdited = medicamentService.findById(medicament.getId());
		
		try {
			medicamentEdited.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(medicament.getDateStringExpiration()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		medicamentService.update(medicamentEdited.getId(), medicamentEdited.getDateExpiration());
		//System.out.println(medicament.getId());
		return mav;
	}
	
	
	
	


}
