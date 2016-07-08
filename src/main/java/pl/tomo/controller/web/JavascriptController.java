package pl.tomo.controller.web;


import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JavascriptController {
	
	@RequestMapping(value = "resources/js/medicaments/functions.js", method = RequestMethod.GET)
    public String getMedicamentsFunction() {
		return "js/medicaments/functions";
    }
	
	@RequestMapping(value = "resources/js/patients/createTable.js", method = RequestMethod.GET)
    public String common() {
		return "js/patients/createTable";
    }

}
