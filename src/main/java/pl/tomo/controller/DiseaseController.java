package pl.tomo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentForm;
import pl.tomo.entity.Patient;
import pl.tomo.entity.PatientForm;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.DosageService;
import pl.tomo.service.FileService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;
import pl.tomo.upload.FileBucket;

@Controller
@RequestMapping(value = "/disease")
@SessionAttributes(value = "patientObj")
public class DiseaseController {
	
	private Logger logger = Logger.getLogger(DiseaseController.class);
	
	private static String UPLOAD_LOCATION="C:/mytemp/";

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
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@Autowired
	private DosageService dosageService;
	
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
			for (Integer id : ids) {
				Medicament medicament = medicamentService.findById(id);
				disease.getMedicaments().add(medicament);
				logger.info("user " + principal.getName() + " add medicaments to disease id " + disease.getId() + ", medicament id: " + medicament.getId());
			}
			diseaseService.save(disease);
			return new ModelAndView("redirect:/disease/list.html");
		}
		logger.info("user " + principal.getName() + " try add medicaments to disease id " + disease.getId() + ", no owner");
		return new ModelAndView("redirect:/no-access.html");
		
	}
	
	@RequestMapping(value = "/addDosage", method = RequestMethod.GET)
	public ModelAndView addDosage(@RequestParam(value = "idd", required = true) int idd, @RequestParam(value = "idm", required = true) int idm) {
		System.out.println(idd);
		System.out.println(idm);
		Medicament medicament = medicamentService.findById(idm);
		if(medicament.getPackageID() == 0) 
			return new ModelAndView("no-access");
		String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
		int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idd, idm).intValue();
		System.out.println(idMD);
		Dosage dosage = new Dosage(medicament);
		dosage.setIdMD(idMD);
		ModelAndView modelAndView = new ModelAndView("dosage");
		modelAndView.addObject("dosage", dosage);
		
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addDosage", method = RequestMethod.POST)
	public ModelAndView saveDosage(@ModelAttribute("dosage") Dosage dosage) {
		System.out.println(dosage.getUnit());
		System.out.println(dosage.getTakeTime());
		System.out.println(dosage.getWholePackage());
		System.out.println(dosage.getDose());
		
		dosageService.save(dosage);
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:/disease/list.html");

		
		
		
		return modelAndView;
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
		int diseaseId = medicamentForm.getDiseaseId();
		Disease disease = diseaseService.findById(diseaseId);
		if(disease.getUser().getName().equals(principal.getName())) {
			for (Integer id : ids) {
				jdbcTemplateMySQL.update("DELETE FROM Disease_Medicament WHERE disease_id=? and medicaments_id=?", new Object[] { diseaseId, id });
				logger.info("user " + principal.getName() + "delete medicaments from disease id " + disease.getId() + ", medicament id: " + id);
			}
			return new ModelAndView("redirect:/disease/list.html");
		}
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
						.onClass(User.class, Match.match().exclude("medicaments").exclude("diseases").exclude("patients").exclude("roles").exclude("files")));
			}
			else
				logger.info("user " + principal.getName() + " try get view medicmanets in disease id: " + id + ", no owner");
		}
		
	}
	//http://www.raistudies.com/spring/spring-mvc/file-upload-spring-mvc-annotation/
	@RequestMapping(value="/{id}/upload", method = RequestMethod.POST)
	public void processFormUpload(@PathVariable("id") int id, HttpSession session, 
			FileBucket fileBucket, Principal principal,
			HttpServletResponse httpServletResponse) throws MaxUploadSizeExceededException
	{
		MultipartFile fileUpload = fileBucket.getFile();
		String userName = principal.getName();
		User user = userService.findByName(userName);
		Disease disease = diseaseService.findById(id);
		pl.tomo.entity.File file = new pl.tomo.entity.File();
		file.setDisease(disease);
		file.setUser(user);
		Date date = new Date();
		file.setUploadDate(date);
		file.setName(fileUpload.getOriginalFilename());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSS");
		String format = simpleDateFormat.format(date);


		File uploadedFile = new File( UPLOAD_LOCATION + format + "-" + fileUpload.getOriginalFilename());
        try {
			FileCopyUtils.copy(fileBucket.getFile().getBytes(), uploadedFile);
			file.setPath(uploadedFile.getAbsolutePath());
			fileService.save(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		
		try {
			httpServletResponse.sendRedirect("/disease/list.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
