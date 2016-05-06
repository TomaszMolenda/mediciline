package pl.tomo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentForm;
import pl.tomo.entity.Patient;
import pl.tomo.entity.PatientForm;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.PatientService;

@Controller
@RequestMapping(value = "/disease")
@SessionAttributes(value = "patientObj")
public class DiseaseController {
	
	private Logger logger = Logger.getLogger(DiseaseController.class);

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private MedicamentService medicamentService;

	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/patient")
	public ModelAndView setSessionPatient(@ModelAttribute("patientForm") PatientForm patientForm, Principal principal) {
		ModelAndView mav = new ModelAndView();
		int id = patientForm.getId();
		Patient patient = patientService.getById(id);
		if(principal.getName().equals(patient.getUser().getName())) {
			mav.addObject("patientObj", patient);
			logger.info("user " + principal.getName() + " set session attribute patient id " + patient.getId());
			mav.setViewName("redirect:/disease/list.html");
		}
		else {
			mav.setViewName("redirect:/no-access.html");
			logger.info("user " + principal.getName() + " try set session attribute patient id " + patient.getId() + ", no owner");
		}	
		return mav;
	}

	@RequestMapping(value = "/patient/delete")
	public ModelAndView deleteSessionPatient(SessionStatus sessionStatus, Principal principal) {
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		sessionStatus.setComplete();
		logger.info("user " + principal.getName() + " remove session attribute");
		return mav;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal, ModelMap modelMap) {
		ModelAndView mav = new ModelAndView("diseases");
		String name = principal.getName();
		List<Patient> patients = patientService.getAllByUser(name);
		PatientForm patientForm = new PatientForm();
		patientForm.setPatients(patients);
		mav.addObject("patientForm", patientForm);
		if (modelMap.containsKey("patientObj")) {
			Patient patient = (Patient) modelMap.get("patientObj");
			if (!patient.getUser().getName().equals(name)) {
				logger.info("user " + principal.getName() + " try get diseases with session attribute patient " + patient.getId() + ", no owner");
				mav.setViewName("no-access");
				return mav;
			}
			List<Disease> diseases = diseaseService.findByPatient(patient);
			mav.addObject("diseases", diseases);
			mav.addObject("disease", new Disease());
			MedicamentForm medicamentForm = new MedicamentForm();
			List<Medicament> medicaments = medicamentService.findByUser(name);
			medicamentForm.setMedicaments(medicaments);
			mav.addObject("medicamentForm", medicamentForm);
			mav.addObject("medicamentRemoveForm", new MedicamentForm());
			
		}
		return mav;
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView addSubmit(@ModelAttribute("disease") Disease disease, Principal principal, ModelMap modelMap) {
		String userName = principal.getName();
		Patient patient = (Patient) modelMap.get("patientObj");
		if(principal.getName().equals(patient.getUser().getName())){
			diseaseService.save(disease, userName, patient);
			logger.info("user " + principal.getName() + "change disease id " + disease.getId());
			return new ModelAndView("redirect:/disease/list.html");
		}
		logger.info("user " + principal.getName() + " try change disease id " + disease.getId() + ", no owner");
		return new ModelAndView("redirect:/no-access.html");
	}

	@RequestMapping(value = "/addMedicaments")
	public ModelAndView addMedicamentsSubmit(@ModelAttribute("medicamentForm") MedicamentForm medicamentForm,
			Principal principal) {
		List<Integer> ids = medicamentForm.getIds();
		Disease disease = diseaseService.findById(medicamentForm.getDiseaseId());
		if(principal.getName().equals(disease.getUser().getName())) {
			List<Medicament> medicaments = medicamentService.findByDisease(disease);
			List<Integer> idMedicaments = new ArrayList<Integer>();
			for (Medicament medicament : medicaments) idMedicaments.add(medicament.getId());
			for (Integer idMedicament : ids) {
				if (!idMedicaments.contains(idMedicament)) {
					Medicament medicament = medicamentService.findById(idMedicament);
					medicaments.add(medicament);
				}
			}
			disease.setMedicaments(medicaments);
			diseaseService.save(disease);
			logger.info("user " + principal.getName() + " add medicaments to disease id " + disease.getId() + ", medicaments id: " + idMedicaments);
			return new ModelAndView("redirect:/disease/list.html");
		}
		logger.info("user " + principal.getName() + " try add medicaments to disease id " + disease.getId() + ", no owner");
		return new ModelAndView("redirect:/no-access.html");
		
	}

	@RequestMapping(value = "/remove/{id}")
	public ModelAndView remove(@PathVariable int id, Principal principal) {
		Disease disease = diseaseService.findByIdWithUser(id);
		if (disease.getUser().getName().equals(principal.getName())) {
			diseaseService.delete(id);
			logger.info("user " + principal.getName() + "delete disease id " + disease.getId());
			return new ModelAndView("redirect:/disease/list.html");
		}
		logger.info("user " + principal.getName() + " try delete disease id " + disease.getId() + ", no owner");
		return new ModelAndView("redirect:/no-access.html");
	}

	@RequestMapping(value = "/removeMedicaments")
	public ModelAndView removeMedicamentsSubmit(@ModelAttribute("medicamentRemoveForm") MedicamentForm medicamentForm,
			Principal principal) {
		List<Integer> ids = medicamentForm.getIds();
		List<Medicament> medicaments = medicamentService.findByDisease(medicamentForm.getDiseaseId());
		if(!medicaments.isEmpty()) {
			Disease disease = medicaments.get(0).getDisease().get(0);
			if(disease.getUser().getName().equals(principal.getName())) {
				for (Integer integer : ids) {
					for (Medicament medicament : medicaments) {
						if (integer.equals(medicament.getId())) {
							medicaments.remove(medicament);
							break;
						}
					}
				}
				disease.setMedicaments(medicaments);
				diseaseService.save(disease);
				logger.info("user " + principal.getName() + "delete medicaments from disease id " + disease.getId() + ", medicaments id: " + ids);
				return new ModelAndView("redirect:/disease/list.html");
			}
		}
		logger.info("user " + principal.getName() + "try delete medicaments from disease id " + medicamentForm.getDiseaseId() + ", no owner");
		return new ModelAndView("redirect:/no-access.html");
	}

	private JsonResult json = JsonResult.instance();

	@RequestMapping(value = "/medicaments", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody void getMedicamentInJSON2(ModelMap modelMap, @RequestParam("id") int id, Principal principal) {
		String name = principal.getName();
		List<Medicament> medicaments = medicamentService.findWithUserByDisease(id);
		if(!medicaments.isEmpty()){
			if(medicaments.get(0).getUser().getName().equals(name)) {
				logger.info("user " + principal.getName() + "get view medicmanets in disease id: " + id);
				json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match().exclude("disease"))
						.onClass(User.class, Match.match().exclude("medicaments").exclude("diseases").exclude("patients").exclude("roles")));
			}
			else
				logger.info("user " + principal.getName() + " try get view medicmanets in disease id: " + id + ", no owner");
		}
		
	}

}
