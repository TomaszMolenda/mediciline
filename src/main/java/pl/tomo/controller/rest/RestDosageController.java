package pl.tomo.controller.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.form.DosageForm;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
import pl.tomo.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class RestDosageController {
	
	private Logger logger = Logger.getLogger(RestDosageController.class);
	
	private JsonResult json = JsonResult.instance();
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private DosageService dosageService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }	

	@RequestMapping(value = "/dosage/add", method=RequestMethod.POST)
	@ResponseBody
	public void saveDosage(@RequestBody Dosage dosage, HttpServletRequest request) {
		Dosage saveedDosage = dosageService.save(dosage, request);
		json.use(JsonView.with(saveedDosage).onClass(Dosage.class, Match.match().exclude("user")));
	}

	@RequestMapping(value = "/dosage/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteDosage(@PathVariable("id") int id, HttpServletRequest request) {
			dosageService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/dosages", method=RequestMethod.GET)
	@ResponseBody
	public void getDosages(@RequestParam("idD") int idD, @RequestParam("idM") int idM, HttpServletRequest request) {
		DosageForm dosages = dosageService.getDosages(request, idD, idM);
		json.use(JsonView.with(dosages).onClass(DosageForm.class, Match.match())
				.onClass(Medicament.class, Match.match()
						.exclude("*")
						.include("name")
						.include("quantity")
						.include("unit"))
				.onClass(Disease.class, Match.match()
						.exclude("*")
						.include("name")));
	}
}
