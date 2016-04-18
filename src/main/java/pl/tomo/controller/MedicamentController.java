package pl.tomo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Medicament;
import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.service.MedicamentAdditionalService;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.MedicamentService;

@Controller
@RequestMapping(value = "/medicament")
@SessionAttributes(value = "sessionDB")
public class MedicamentController {
	
	private Logger logger = Logger.getLogger(MedicamentController.class);
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	@Autowired
	private MedicamentAdditionalService medicamentAdditionalService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal, ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("medicaments");
		String name = principal.getName();
		Medicament medicament = new Medicament();
		List<Medicament> medicaments = medicamentService.findByUser(name);
		modelAndView.addObject("medicaments", medicaments);
		modelAndView.addObject("medicament", medicament);
		createSessionDB(modelMap, modelAndView);
		logger.info("User " + name + " open medicament/list");
		return modelAndView;
	}

	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView change(@Valid @ModelAttribute("medicament") Medicament medicament, 
			BindingResult result, Principal principal) {
		String name = principal.getName();
		if(result.hasErrors()) {
			logger.info("User " + name + " try change medicament - no success");
			return new ModelAndView("redirect:/no-access.html");
		}
		if(medicament.getId() == 0) logger.info("User " + name + " added medicament " + medicament.getName() + ", id: " + medicament.getId());
		else logger.info("User " + name + " changed medicament " + medicament.getName() + ", id: " + medicament.getId());
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
			logger.info("User " + name + " removed medicament " + medicament.getName() + ", id: " + medicament.getId());
			return new ModelAndView("redirect:/medicament/list.html");
		}
		logger.info("User " + name + " try remove not own medicament " + medicament.getName() + ", id: " + medicament.getId());
		return new ModelAndView("redirect:/no-access.html");
	}
	
	@RequestMapping(value = "/database")
	public ModelAndView medicamentsDatabase(ModelMap modelMap, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("medicaments-database");
		createSessionDB(modelMap, modelAndView);
		logger.info("User " + principal.getName() + " open database");
		return modelAndView;
	}
	
	private JsonResult json = JsonResult.instance();
	
	@RequestMapping(value="/database", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody void getMedicamentInJSON2(ModelMap modelMap, 
			@RequestParam("session") String session, 
			@RequestParam("search") String search,
			Principal principal) {
		String sessionDB = (String) modelMap.get("sessionDB");
		if(sessionDB != null & sessionDB.equals(session) & search.length() >= 3) {
			List<pl.tomo.medicament.entity.Medicament> list = medicamentMService.getMedicamentBySearch(search);
			json.use(JsonView.with(list).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match().exclude("*")
					.include("productName")
					.include("form")
					.include("price")
					.include("pack")
					.include("dosage")
					.include("productLineID")
					.include("packageID")
					.include("producer")));
			logger.info("User " + principal.getName() + " get medicaments json (database) - search: " + search);
		}
		
	}
	
	@RequestMapping(value="/database/additional", method = RequestMethod.GET)
	public @ResponseBody void getMedicamentAdditionalInJSON(ModelMap modelMap, 
			@RequestParam("session") String session, 
			@RequestParam("productLineID") int productLineID,
			Principal principal) {
		String sessionDB = (String) modelMap.get("sessionDB");
		if(sessionDB != null & sessionDB.equals(session)) {
			MedicamentAdditional medicamentAdditional = medicamentAdditionalService.getById(productLineID);
			if(medicamentAdditional != null) {
				json.use(JsonView.with(medicamentAdditional).onClass(MedicamentAdditional.class, Match.match()));
				logger.info("User " + principal.getName() + " get medicament additional json (database), medicament productLineID: " + productLineID);
			}
			
		}
	}

	private void createSessionDB(ModelMap modelMap, ModelAndView modelAndView) {
		String object = (String) modelMap.get("sessionDB");
		if(object == null) {
			String sessionDB = UUID.randomUUID().toString();
			modelAndView.addObject("sessionDB", sessionDB);
		}
	}

}
