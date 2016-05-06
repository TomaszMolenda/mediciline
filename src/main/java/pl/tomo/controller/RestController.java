package pl.tomo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class RestController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicamentMService medicamentMService; 
	
	@RequestMapping(value = "/login/{username}/{password}", headers="Accept=application/json")
	@ResponseBody
	public void login(@PathVariable("username") String userName, @PathVariable("password") String password) {
		
		User user = userService.findByEmail(userName);
		if(user != null) {
			if(user.getPassword().equals(password)) {
				json.use(JsonView.with(user).onClass(User.class, Match.match().exclude("*")
						.include("id")
						.include("name")
						.include("uniqueID")
						.include("password")));
			}
		}
	}
	
	@RequestMapping(value = "/medicaments/{uniqueId}", headers="Accept=application/json")
	@ResponseBody
	public void getMedicaments(@PathVariable("uniqueId") String uniqueID) {
		User user = userService.findByUniqueID(uniqueID);
		List<Medicament> medicaments = medicamentService.findByUser(user.getName());

		if(user != null) {
				json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match().exclude("*")
						.include("id")
						.include("name")
						.include("producent")
						.include("price")
						.include("kind")));
		}
	}
	
	@RequestMapping(value = "/medicamentsdb", headers="Accept=application/json")
	@ResponseBody
	public void getMedicamentsDb() {
		
		List<pl.tomo.medicament.entity.Medicament> medicaments = medicamentMService.getAllMedicaments();
		List<pl.tomo.medicament.entity.Medicament> med = new ArrayList<pl.tomo.medicament.entity.Medicament>();
		for (int i = 0; i < 10; i++) {
			med.add(medicaments.get(i));
		}
		json.use(JsonView.with(medicaments).onClass(pl.tomo.medicament.entity.Medicament.class, Match.match()
				.exclude("medicamentAdditional")
				.exclude("atcs")
				.exclude("distributor")
				.exclude("productType")
				.exclude("prescription")
				.exclude("diseases")));
		
	}
	
	@RequestMapping(value = "/medicament/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer saveMedicament(@RequestBody pl.tomo.medicament.entity.Medicament medicament) {

		return 1;
		
	}

}
