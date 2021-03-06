package pl.tomo.controller.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

@RestController
@RequestMapping(value = "/api")
public class RestDosageController {
	
	private JsonResult json = JsonResult.instance();
	
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
	public ResponseEntity<?> saveDosage(@RequestBody Dosage dosage, HttpServletRequest request) {
		Dosage saveedDosage = dosageService.save(dosage, request);
		Dosage returnValue = json.use(JsonView.with(saveedDosage)
				.onClass(Dosage.class, Match.match()
						.exclude("*")
						.include("id")
						.include("idServer"))).returnValue();
		return new ResponseEntity<Dosage>(returnValue, HttpStatus.OK);
	}

	@RequestMapping(value = "/dosage/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteDosage(@PathVariable("id") int id, HttpServletRequest request) {
			dosageService.delete(id, request);
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dosage/email/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sendDosagesByEmail(@PathVariable("id") int diseaseId, @RequestParam("email") String email, HttpServletRequest request) {
		dosageService.sendEmail(email, diseaseId, request);
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
						.include("name"))
				.onClass(Dosage.class, Match.match()
						.exclude("*")
						.include("id")
						.include("dose")
						.include("takeTime")));
	}
}
