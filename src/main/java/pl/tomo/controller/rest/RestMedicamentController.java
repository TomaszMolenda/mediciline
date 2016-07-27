package pl.tomo.controller.rest;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.controller.exception.AdditionalInformationException;
import pl.tomo.controller.exception.UserNotFoundException;
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
@RequestMapping(value = "/api")
public class RestMedicamentController {
		
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/medicament", method=RequestMethod.POST)
	@ResponseBody
	public void saveMedicament(HttpServletRequest request, @RequestBody Medicament medicament) {
		medicament = medicamentService.save(medicament, request);
		json.use(JsonView.with(medicament).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
	}
	
	@RequestMapping(value = "/medicaments/save", method=RequestMethod.POST)
	@ResponseBody
	public void saveMedicaments(HttpServletRequest request, @RequestBody List<Medicament> medicaments) {
		medicaments = medicamentService.save(medicaments, request);
		json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
	}
	
	@RequestMapping(value = "/medicament/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void getMedicament(@PathVariable("id") int id, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		Medicament medicament = medicamentService.findById(id);
		if(user.equals(medicament.getUser()))
			json.use(JsonView.with(medicament).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
		else throw new UserNotFoundException(request);
	}
	
	
	@RequestMapping(value="/medicamentsdb/search", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody void getMedicamentsDbInJSON(@RequestParam("search") String search, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
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
			}
		
	}
	
	@RequestMapping(value="/medicaments/information", method = RequestMethod.GET)
	@ResponseBody
	public void getMedicamentAdditionalInJSON(ModelMap modelMap, @RequestParam("id") int id, HttpServletRequest request) {
			pl.tomo.medicament.entity.Medicament medicamentM = null;
			try {
				medicamentM = medicamentMService.getMedicament(id, request);
			} catch (NoResultException e) {
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
				}
	}
}
