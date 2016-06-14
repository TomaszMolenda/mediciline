package pl.tomo.controller.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
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

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
import pl.tomo.service.MedicamentService;
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
	
	//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
    class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(HttpServletRequest request) {
			logger.info("No access from ip " + request.getRemoteAddr() + " (No such user)");
		}
    }
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such element")
    class NoSuchElementException extends RuntimeException {
        public NoSuchElementException(HttpServletRequest request) {
			logger.info("No access from ip " + request.getRemoteAddr() + " (No such element)");
		}
    }
	
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
		Medicament medicament = medicamentService.findByIdWithUser(id);
		if(user.getName().equals(medicament.getUser().getName()))
			json.use(JsonView.with(medicament).onClass(Medicament.class, Match.match()
				.exclude("user")
				.exclude("disease")));
		else throw new UserNotFoundException(request);
	}
	
	@RequestMapping(value = "/medicaments/{uniqueId}", headers="Accept=application/json")
	@ResponseBody
	public void getMedicaments(@PathVariable("uniqueId") String uniqueID) {
		User user = userService.findByUniqueID(uniqueID);
		List<Medicament> medicaments = medicamentService.findByUser(user.getName());
		if(user != null) {
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
}
