package pl.tomo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.User;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class RestController {
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", headers="Accept=application/json")
	@ResponseBody
	public void login(HttpServletRequest request) {
		String userName = request.getHeader("userName");
		String password = request.getHeader("password");
		User user = userService.findByEmail(userName);
		if(user != null) {
			if(user.getPassword().equals(password)) {
				json.use(JsonView.with(user).onClass(User.class, Match.match().exclude("*")
						.include("id")
						.include("name")
						.include("uniqueID")
						.include("password")
						.include("email")
						.include("auth")));
			}
		}
	}
	
	
	
	
	
	
	
	
	

}
