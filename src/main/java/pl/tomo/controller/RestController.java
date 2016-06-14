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
	private UserService userService;

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
				logger.info("user " + user.getName() + " logged in rest client");
			}
		}
	}
	
	
	
	
	
	
	
	
	

}
