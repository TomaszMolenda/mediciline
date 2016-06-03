package pl.tomo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
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
import org.springframework.web.servlet.ModelAndView;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

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
import pl.tomo.provider.PagedResource;
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
	
	@Autowired
	private pl.tomo.repository.TestRepository testRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal, ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("medicaments");
		Medicament medicament = new Medicament();
		List<Medicament> medicaments = medicamentService.findByUser(principal.getName());
		modelAndView.addObject("medicaments", medicaments);
		modelAndView.addObject("medicament", medicament);
		logger.info("user " + principal.getName() + " open medicament/list");
		return modelAndView;
	}

	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView change(@Valid @ModelAttribute("medicament") Medicament medicament, 
			BindingResult result, Principal principal) {
		System.out.println("getPackageID " + medicament.getPackageID());
		String name = principal.getName();
		if(result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError objectError : allErrors) {
				System.out.println(objectError);
			}
			logger.info("user " + name + " try change medicament - no success");
			return new ModelAndView("redirect:/no-access.html");
		}
		if(medicament.getId() == 0) logger.info("user " + name + " added medicament " + medicament.getName() + ", id: " + medicament.getId());
		else logger.info("user " + name + " changed medicament " + medicament.getName() + ", id: " + medicament.getId());
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
			String sqlIdMD = "select id from Disease_Medicament where medicaments_id=?";
			
			try {
				int idMD = jdbcTemplateMySQL.queryForObject(sqlIdMD, Integer.class, medicament.getId()).intValue();
				String sqlDelete = "delete from Dosage where idMD=" + idMD;
				logger.info("user " + name + " removed dosages, id: " + idMD);
				int update = jdbcTemplateMySQL.update(sqlDelete);
			} catch (EmptyResultDataAccessException e) {
				
			}
			
			
			

			medicamentService.delete(id);
			logger.info("user " + name + " removed medicament " + medicament.getName() + ", id: " + medicament.getId());
			return new ModelAndView("redirect:/medicament/list.html");
		}
		logger.info("user " + name + " try remove not own medicament " + medicament.getName() + ", id: " + medicament.getId());
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
	public @ResponseBody void getMedicamentInJSON2(ModelMap modelMap,
			@RequestParam("search") String search,
			HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
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
	public void getMedicamentAdditionalInJSON(ModelMap modelMap,
			@RequestParam("packageID") int packageID,
			HttpServletRequest request) {
		String auth = getAuthCookie(request);
		User user = userService.findByAuth(auth);
		if(user!=null) {
			pl.tomo.medicament.entity.Medicament medicament = medicamentMService.getMedicamentByPackageID(packageID);
			if(medicament != null) {
				json.use(JsonView.with(medicament).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match())
						.onClass(MedicamentAdditional.class, Match.match().exclude("medicaments"))
						.onClass(ATC.class, Match.match().exclude("medicaments"))
						.onClass(Distributor.class, Match.match().exclude("medicaments"))
						.onClass(ProductType.class, Match.match().exclude("medicaments"))
						.onClass(Prescription.class, Match.match().exclude("medicaments"))
						.onClass(Disease.class, Match.match().exclude("medicaments")));
				logger.info("User " + user.getName() + " get medicament information json (database), medicament packageID: " + packageID);
			}
		}
			
		
	}

	
	@RequestMapping(value = "/test", headers="Accept=application/json")
	@ResponseBody
	public void test(@RequestParam int page, @RequestParam int size) {
		Pageable pageable = new PageRequest(page, size, new Sort("id"));
		Page<Medicament> pageResult = testRepository.findAll(pageable);
		List<Medicament> content = pageResult.getContent();
		PagedResource<Medicament> pagedResource = new PagedResource<Medicament>(pageResult, content);
		
		json.use(JsonView.with(pagedResource).onClass(Medicament.class, Match.match().exclude("*").include("id").include("name")));
	}
	
	private String getAuthCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("AUTH"))
				return cookie.getValue();
		}
		return null;
	}

}
