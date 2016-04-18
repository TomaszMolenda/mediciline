package pl.tomo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pl.tomo.provider.EmailService;
import pl.tomo.service.UserService;
import pl.tomo.validator.UserValidator;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister()
	{
		return new ModelAndView("register", "user", new User());
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegister(@Valid @ModelAttribute("user") User user, BindingResult result)
	{
		user.setName(user.getName().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		userValidator.validate(user, result);
		if(result.hasErrors()) return new ModelAndView("register");
		Role role = userService.findRoleByName("ROLE_USER");
		user.getRoles().add(role);
		user.setUniqueID(UUID.randomUUID().toString());
		userService.save(user);
		logger.info("User " + user.getName() + " with email " + user.getEmail() + " has registered");
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
	
	@RequestMapping(value = "/user/account")
	public ModelAndView account() {
		ModelAndView modelAndView = new ModelAndView("account");
		return modelAndView;
	}
	
	@RequestMapping(value = "/user/admin")
	public ModelAndView admin() throws Exception {
		ModelAndView modelAndView = new ModelAndView("admin");
		
		logger.info("Start backup postgres");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = simpleDateFormat.format(date);
		Runtime r = Runtime.getRuntime();
		Process p;
		ProcessBuilder pb;
		r = Runtime.getRuntime();
		pb = new ProcessBuilder(
			"C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_dump.exe",
			"--dbname=postgresql://postgres:tomo@127.0.0.1:5432/test2",
			"--format",  "custom",
			"--blobs",
			"--verbose",
			"--file", "C:\\Users\\tmolenda\\Documents\\" + format + ".backup"	
			);
				
//		    "C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_dump.exe",
//		    "--host", "localhost",
//		    "--port", "5432",
//		    "--username", "postgres",
//		    "--no-password",
//		    "--format", "custom",
//		    "--blobs",
//		    "--verbose",
//		    "--file", "C:\\Users\\tmolenda\\Documents\\test2trutyre.backup",
//		    "test2");
		
		pb.redirectErrorStream(true);
		p = pb.start();
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ll;
		while ((ll = br.readLine()) != null) {
			logger.info(ll);
		 System.out.println(ll);
		}              
		logger.info("Finish backup postgres");
		return modelAndView;
	}

}
