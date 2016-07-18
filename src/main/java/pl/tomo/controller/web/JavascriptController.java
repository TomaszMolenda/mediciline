package pl.tomo.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JavascriptController {
	
	@RequestMapping(value = "resources/js/medicaments/functions.js", method = RequestMethod.GET)
    public String getMedicamentsFunction() {
		return "js/medicaments/functions";
    }
	
	@RequestMapping(value = "resources/js/diseases/functions.js", method = RequestMethod.GET)
    public String getDiseasesFunction() {
		return "js/diseases/functions";
    }
	
	@RequestMapping(value = "resources/js/diseases/functions-info.js", method = RequestMethod.GET)
    public String getDiseaseInfoFunction() {
		return "js/diseases/functions-info";
    }
	
	@RequestMapping(value = "resources/js/dosages/functions.js", method = RequestMethod.GET)
    public String getDosageFunction() {
		return "js/dosages/functions";
    }
	
	
	@RequestMapping(value = "resources/js/patients/functions.js", method = RequestMethod.GET)
    public String getPatientFunction() {
		return "js/patients/functions";
    }
	
	@RequestMapping(value = "resources/js/user/functions.js", method = RequestMethod.GET)
    public String getUserFunction() {
		return "js/user/functions";
    }

}
