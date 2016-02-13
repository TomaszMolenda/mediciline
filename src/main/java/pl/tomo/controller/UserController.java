package pl.tomo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.provider.Email;
import pl.tomo.service.EmailService;
import pl.tomo.service.UserService;
import pl.tomo.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(Model model)
	{
		model.addAttribute("user", new User());
		return "userRegister";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result)
	{
		user.setName(user.getName().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		
		
		userValidator.validate(user, result);
		if(result.hasErrors())
		{
			return "userRegister";
		}
		String roleName = "ROLE_USER";
		Role role = userService.findRoleByName(roleName);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		user.setUniqueID(UUID.randomUUID().toString());
		user.setActive(false);
		userService.save(user);
		emailService.sendEmail(user.getEmail(), user.getUniqueID());
		
		return "redirect:/index.html";
	}
	
	@RequestMapping(value = "/login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping(value = "/confirm/{uniqueID}")
	public ModelAndView confirm(@PathVariable String uniqueID)
	{
		User user = userService.findByUniqueID(uniqueID);
		user.setActive(true);
		userService.save(user);
		ModelAndView mav = new ModelAndView("userConfirm");
		
		return mav;
	}

}
