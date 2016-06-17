package pl.tomo.controller.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.controller.exception.UserNotFoundException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/api")
public class RestDosageController {
	
	private Logger logger = Logger.getLogger(RestDosageController.class);
	
	private JsonResult json = JsonResult.instance();
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
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
		User user = userService.findByRequest(request);
		if(user != null) {
			dosage.setUser(user);
			Dosage saveedDosage = dosageService.save(dosage);
			logger.info("save dosage, id: " + saveedDosage.getId());
			json.use(JsonView.with(saveedDosage).onClass(Dosage.class, Match.match()));
		}
		else
			throw new UserNotFoundException(request);
	}


	@RequestMapping(value = "/dosage/info", method=RequestMethod.GET)
	@ResponseBody
	public void getDosageInfo(@RequestParam("idd") int idd, @RequestParam("idm") int idm, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Medicament medicament = medicamentService.findById(idm);
		Disease disease = diseaseService.findByIdWithUser(idd);
		if(medicament.getUser().equals(user) & disease.getUser().equals(user)) {
			String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
			int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idd, idm).intValue();
			Dosage dosage = new Dosage(medicament.getKind());
			dosage.setIdMD(idMD);
			logger.info("get dosage info, id: " + dosage.getId());
			json.use(JsonView.with(dosage).onClass(Dosage.class, Match.match()));
		} else
			throw new UserNotFoundException(request);	
	}
	
	@RequestMapping(value = "/dosage/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public String deleteDosage(@PathVariable("id") int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Dosage dosage = dosageService.findById(id);
		if(dosage.getUser().equals(user)) {
			dosageService.delete(id);
			return "ok";
		}
		else
			throw new UserNotFoundException(request);
		
	}
	
	@RequestMapping(value = "/dosages", method=RequestMethod.GET)
	@ResponseBody
	public void getDosages(@RequestParam("idd") int idd, @RequestParam("idm") int idm, HttpServletRequest request) {
		Medicament medicament = medicamentService.findById(idm);
		Disease disease = diseaseService.findByIdWithUser(idd);
		User user = userService.findByRequest(request);
		if(medicament.getUser().equals(user) & disease.getUser().equals(user)) {
			String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
			int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idd, idm).intValue();
			List<Dosage> dosagesList = dosageService.getDosages(idMD);
			Set<Dosage> dosages = new HashSet<Dosage>(dosagesList);
			json.use(JsonView.with(dosagesList).onClass(Dosage.class, Match.match()));
		} else
			throw new UserNotFoundException(request);
	}
}
