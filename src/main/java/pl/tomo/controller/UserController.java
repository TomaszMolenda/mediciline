package pl.tomo.controller;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.entity.form.UserChangePassword;
import pl.tomo.provider.EmailService;
import pl.tomo.service.UserService;
import pl.tomo.validator.ChangePasswordValidator;
import pl.tomo.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister()
	{
		return new ModelAndView("user/register");
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, Principal principal)
	{
		if(principal == null) {
			return new ModelAndView("user/login");
		}
		return new ModelAndView("index");
		
	}
	
	@RequestMapping(value = "/confirm/{uniqueID}")
	public ModelAndView confirm(@PathVariable String uniqueID)
	{
		ModelAndView modelAndView = new ModelAndView("user/confirm");
		User user = userService.confimr(uniqueID);
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	

	
	
	
	

}
