package pl.tomo.controller.rest;

import java.security.Principal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.controller.exception.UserNotFoundException;
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
import pl.tomo.service.RequestService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class RestMedicamentController {
	
	private Logger logger = Logger.getLogger(RestMedicamentController.class);
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/medicament/save", method=RequestMethod.POST)
	@ResponseBody
	public void saveMedicament(@RequestBody Medicament medicament) {
		int id = medicament.getId();
		medicament = medicamentService.save(medicament, "pina");
		medicament.setIdServer(medicament.getId());
		medicament.setId(id);
		json.use(JsonView.with(medicament).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
	}
	
	@RequestMapping(value = "/medicaments/save", method=RequestMethod.POST)
	@ResponseBody
	public void saveMedicaments(@RequestBody List<Medicament> medicaments) {
		medicaments = medicamentService.save(medicaments, "pina");
		json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
	}
	
	@RequestMapping(value = "/medicament/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void getMedicament(@PathVariable("id") int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Medicament medicament = medicamentService.findById(id);
		if(user.getName().equals(medicament.getUser().getName()))
			json.use(JsonView.with(medicament).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
		else throw new UserNotFoundException(request);
	}
	
	@RequestMapping(value = "/medicaments/{uniqueId}", headers="Accept=application/json")
	@ResponseBody
	public void getMedicaments(@PathVariable("uniqueId") String uniqueID, HttpServletRequest request) {
		User user = userService.findByUniqueID(uniqueID);
		if(user != null) {
			List<Medicament> medicaments = medicamentService.findAll(user);
			json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match().exclude("*")
					.include("idServer")
					.include("name")
					.include("producent")
					.include("price")
					.include("kind")
					.include("date")
					.include("productLineID")
					.include("packageID")));
			
		}
	}
	
	@RequestMapping(value = "/medicamentsdb", headers="Accept=application/json")
	@ResponseBody
	public void getMedicamentsDb() {
		
		List<pl.tomo.medicament.entity.Medicament> medicaments = medicamentMService.getAllMedicaments();

		json.use(JsonView.with(medicaments).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match()
				.exclude("medicamentAdditional")
				.exclude("atcs")
				.exclude("distributor")
				.exclude("productType")
				.exclude("prescription")
				.exclude("diseases")));
		
	}
	
	@RequestMapping(value = "/medicamentsdb/count", headers="Accept=application/json")
	@ResponseBody
	public Integer getMedicamentsDbCount() {
		
		List<pl.tomo.medicament.entity.Medicament> medicaments = medicamentMService.getAllMedicaments();
		return medicaments.size();
		
	}
	
	@RequestMapping(value="/medicamentsdb/information", method = RequestMethod.GET)
	@ResponseBody
	public void getMedicamentAdditionalInJSON(ModelMap modelMap, @RequestParam("packageID") int packageID, HttpServletRequest request, Principal principal) {
			pl.tomo.medicament.entity.Medicament medicamentM = null;
			try {
				medicamentM = medicamentMService.getMedicamentByPackageID(packageID);
			} catch (NoResultException e) {
				logger.info("User " + principal.getName() + "  try get medicament information json (database) - no success - medicament edited");
				return;
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
