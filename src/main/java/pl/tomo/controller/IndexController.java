package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentDb;
import pl.tomo.entity.User;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.MedicamentDbService;
import pl.tomo.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	private MedicamentDbService medicamentDbService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicamentService medicamentService;
	
//	@RequestMapping(value = "/index", method = RequestMethod.GET)
//	public String index(Model model, Principal principal)
//	{
//		String name = principal.getName();
//		User user = userService.findByName(name);
//		model.addAttribute("medicament", medicamentService.findByUser(user));
//		return "index";
//	}
	
	@RequestMapping(value = "/index")
	public String index()
	{
		
		return "index";
	}
	
	@RequestMapping(value = "/no-access")
	public String noAccess()
	{
		return "no-access";
	}

}
