package pl.tomo.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.controller.exception.AdditionalInformationException;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.medicament.entity.ATC;
import pl.tomo.medicament.entity.Disease;
import pl.tomo.medicament.entity.Distributor;
import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.entity.Prescription;
import pl.tomo.medicament.entity.ProductType;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/medicament")
public class MedicamentController {
	
	private Logger logger = Logger.getLogger(MedicamentController.class);
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		ModelAndView modelAndView = new ModelAndView("medicaments");
		List<Medicament> medicaments = medicamentService.findAllActive(user);
		modelAndView.addObject("medicaments", medicaments);
		modelAndView.addObject("medicament", new Medicament());
		logger.info("user " + user.getName() + " open medicament/list");
		return modelAndView;
	}

	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView change(@Valid @ModelAttribute("medicament") Medicament medicament, 
			BindingResult result, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		String name = user.getName();
		if(result.hasErrors() | medicament.isArchive()) {
			logger.info("user " + name + " try change medicament - no success");
			return new ModelAndView("redirect:/no-access.html");
		}
		if(medicament.getId() == 0) logger.info("user " + name + " added medicament " + medicament.getName() + ", id: " + medicament.getId());
		else logger.info("user " + name + " changed medicament " + medicament.getName() + ", id: " + medicament.getId());
		medicamentService.save(medicament, name);
		return new ModelAndView("redirect:/medicament/list.html");
	}
	
	@RequestMapping(value = "/archive/{id}")
	public ModelAndView remove(@PathVariable int id, HttpServletRequest request)
	{
		User user = userService.findByRequest(request);
		String name = user.getName();
		Medicament medicament = medicamentService.findById(id);
		if(medicament.getUser().equals(user))
		{
			medicamentService.archive(medicament);
			logger.info("user " + name + " archived medicament " + medicament.getName() + ", id: " + medicament.getId());
			return new ModelAndView("redirect:/medicament/list.html");
		}
		logger.info("user " + name + " tried archive not own medicament " + medicament.getName() + ", id: " + medicament.getId());
		return new ModelAndView("redirect:/no-access.html");
	}
	
	@RequestMapping(value = "/database")
	public ModelAndView medicamentsDatabase(ModelMap modelMap, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("medicaments-database");
		logger.info("user " + principal.getName() + " open database");
		return modelAndView;
	}
	
	private JsonResult json = JsonResult.instance();
	
	@RequestMapping(value="/database", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody void getMedicamentInJSON2(ModelMap modelMap, @RequestParam("search") String search, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(user != null && search.length() >= 3) {
			List<pl.tomo.medicament.entity.Medicament> list = medicamentMService.getMedicamentBySearch(search);
			json.use(JsonView.with(list).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match().exclude("*")
					.include("productName")
					.include("form")
					.include("price")
					.include("pack")
					.include("dosage")
					.include("productLineID")
					.include("packageID")
					.include("producer")
					.include("dosageObject"))
					.onClass(Dosage.class, Match.match().include("*")));
			logger.info("User " + user.getName() + " get medicaments json (database) - search: " + search);
		}
		
	}
	
	@RequestMapping(value="/database/information", method = RequestMethod.GET)
	@ResponseBody
	public void getMedicamentAdditionalInJSON(ModelMap modelMap, @RequestParam("id") int id, HttpServletRequest request, Principal principal) {
			pl.tomo.medicament.entity.Medicament medicamentM = null;
			try {
				medicamentM = medicamentMService.getMedicament(id, request);
			} catch (NoResultException e) {
				logger.info("User " + principal.getName() + "  try get medicament information json (database) - no success - medicament edited");
				throw new AdditionalInformationException(request);
			}
			if(medicamentM != null) {
				json.use(JsonView.with(medicamentM).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match())
						.onClass(MedicamentAdditional.class, Match.match().exclude("medicaments"))
						.onClass(ATC.class, Match.match().exclude("medicaments"))
						.onClass(Distributor.class, Match.match().exclude("medicaments"))
						.onClass(ProductType.class, Match.match().exclude("medicaments"))
						.onClass(Prescription.class, Match.match().exclude("medicaments"))
						.onClass(Disease.class, Match.match().exclude("medicaments")));
				logger.info("User " + principal.getName() + " get medicament information json (database), medicament packageID: " + medicamentM.getPackageID());
			}
	}

}
