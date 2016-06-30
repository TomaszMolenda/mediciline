package pl.tomo.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

import pl.tomo.controller.exception.NoSaveFileException;
import pl.tomo.controller.exception.UserNotFoundException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.entity.form.MedicamentForm;
import pl.tomo.entity.form.PatientForm;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.FileService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;
import pl.tomo.upload.FileBucket;

@Controller
@RequestMapping(value = "/disease1")
@SessionAttributes(value = "patientObj")
public class DiseaseControllerBAD {
	
	private Logger logger = Logger.getLogger(DiseaseControllerBAD.class);

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private MedicamentService medicamentService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

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
	public ModelAndView list(Principal principal, ModelMap modelMap, HttpServletRequest request) {
		User user = userService.findByRequest(request);
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
			
			medicamentForm.setMedicaments(medicamentService.findAll(user));
			mav.addObject("medicamentForm", medicamentForm);
			mav.addObject("fileBucket", new FileBucket());
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
			diseaseService.save(disease, ids);
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
	public ModelAndView removeMedicamentsSubmit(@ModelAttribute("medicamentRemoveForm") MedicamentForm medicamentForm, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		medicamentForm.setUser(user);
		Disease disease = diseaseService.findById(medicamentForm.getDiseaseId());
		if(disease.getUser().equals(user)) {
			diseaseService.delete(medicamentForm);
			return new ModelAndView("redirect:/disease/list.html");
		}
		return new ModelAndView("redirect:/no-access.html");
	}
	

	private JsonResult json = JsonResult.instance();

	@RequestMapping(value = "/medicaments", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody void getMedicamentInJSON2(ModelMap modelMap, @RequestParam("id") int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Disease disease = diseaseService.findById(id);
		if(disease == null) {
			//TODO throw
		}
		if(disease.getUser().equals(user)) {
			Set<Medicament> medicaments = disease.getMedicaments();
			json.use(JsonView.with(medicaments).onClass(Medicament.class, Match.match().exclude("disease"))
					.onClass(User.class, Match.match()
							.exclude("medicaments")
							.exclude("diseases")
							.exclude("patients")
							.exclude("roles")
							.exclude("files")
							.exclude("dosages")));
		}
		else {
			throw new UserNotFoundException(request);
		}
	}
	//http://www.raistudies.com/spring/spring-mvc/file-upload-spring-mvc-annotation/
	@RequestMapping(value="/{id}/upload", method = RequestMethod.POST)
	public void processFormUpload(@PathVariable("id") int id, HttpServletRequest request, 
			FileBucket fileBucket, Principal principal,
			HttpServletResponse httpServletResponse)
	{
		try {
			fileService.save(fileBucket, request, id);
		} catch (IOException e1) {
			throw new  NoSaveFileException(request);
		}

		try {
			httpServletResponse.sendRedirect("/disease/list.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
