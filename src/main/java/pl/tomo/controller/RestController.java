package pl.tomo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
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
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@Autowired
	private DosageService dosageService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	

	
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
						.include("idServer")
						.include("name")
						.include("producent")
						.include("price")
						.include("kind")
						.include("dateExpiration")
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
