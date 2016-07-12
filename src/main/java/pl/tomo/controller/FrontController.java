package pl.tomo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jcabi.aspects.Loggable;

@Controller
@Loggable
public class FrontController {
	
	@RequestMapping(value = "/")
	public ModelAndView showRegister(HttpServletRequest request)
	{
		return new ModelAndView("index");
	}

}
