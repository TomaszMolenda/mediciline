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

import com.jcabi.aspects.Loggable;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.entity.form.UserChangePassword;
import pl.tomo.provider.EmailService;
import pl.tomo.service.UserService;
import pl.tomo.validator.ChangePasswordValidator;
import pl.tomo.validator.UserValidator;

@Controller
@Loggable
public class UserController {
	
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
		return new ModelAndView("register", "user", new User());
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request)
	{
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return new ModelAndView("register");
		}
		prepareUser(user);
		userService.save(user);
		emailService.sendEmail(user.getEmail(), user.getUniqueID());
		ModelAndView modelAndView = new ModelAndView("register-info");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	private void prepareUser(User user) {
		user.setName(user.getName().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		Role role = userService.findRoleByName("ROLE_USER");
		user.getRoles().add(role);
		user.setUniqueID(UUID.randomUUID().toString());
		user.setAuth(UUID.randomUUID().toString());
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
		User user = userService.findByUniqueID(uniqueID);
		user.setActive(true);
		userService.save(user);
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
			return new ModelAndView("changepassword");
		}
		User user = userService.findByName(principal.getName());
		user.setPassword(userChangePassword.getPassword());
		userService.save(user);
		return new ModelAndView("redirect:/user/account.html");
	}

	
	
	
	

}
