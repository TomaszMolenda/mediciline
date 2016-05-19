package pl.tomo.controller;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import pl.tomo.entity.UserChangePassword;
import pl.tomo.provider.EmailService;
import pl.tomo.service.UserService;
import pl.tomo.validator.ChangePasswordValidator;
import pl.tomo.validator.UserValidator;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request)
	{
		logger.info("open register page from ip " + request.getRemoteAddr());
		return new ModelAndView("register", "user", new User());
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request)
	{
		user.setName(user.getName().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			logger.info("register form has error: " + result.getFieldErrors() + ", ip " + request.getRemoteAddr());
			return new ModelAndView("register");
		}
		Role role = userService.findRoleByName("ROLE_USER");
		user.getRoles().add(role);
		user.setUniqueID(UUID.randomUUID().toString());
		userService.save(user);
		logger.info("User " + user.getName() + " with email " + user.getEmail() + " has registered, ip " + request.getRemoteAddr());
		emailService.sendEmail(user.getEmail(), user.getUniqueID());
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request)
	{
		logger.info("Open login page from IP: " + request.getRemoteAddr());
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/confirm/{uniqueID}")
	public ModelAndView confirm(@PathVariable String uniqueID)
	{
		User user = userService.findByUniqueID(uniqueID);
		user.setActive(true);
		userService.save(user);
		logger.info("User " + user.getName() + " is active");
		return new ModelAndView("register-confirm");
	}
	
	@RequestMapping(value = "/user/account", method = RequestMethod.GET)
	public ModelAndView account() {
		ModelAndView modelAndView = new ModelAndView("account");
		UserChangePassword userChangePassword = new UserChangePassword();
		modelAndView.addObject("userChangePassword", userChangePassword);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/user/account/changepassword", method = RequestMethod.GET)
	public ModelAndView changePasswordGet() {
		ModelAndView modelAndView = new ModelAndView("changepassword");
		UserChangePassword userChangePassword = new UserChangePassword();
		modelAndView.addObject("userChangePassword", userChangePassword);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/user/account/changepassword", method = RequestMethod.POST)
	public ModelAndView changePasswordPost(@Valid @ModelAttribute("userChangePassword") UserChangePassword userChangePassword, BindingResult result, HttpServletRequest request, Principal principal) {
		changePasswordValidator.validate(userChangePassword, result);
		if(result.hasErrors()) {
			logger.info("register form has error: " + result.getFieldErrors() + ", ip " + request.getRemoteAddr());
			return new ModelAndView("changepassword");
		}
		User user = userService.findByName(principal.getName());
		user.setPassword(userChangePassword.getPassword());
		userService.save(user);
		return new ModelAndView("redirect:/user/account.html");
	}

	
	
	
	

}
