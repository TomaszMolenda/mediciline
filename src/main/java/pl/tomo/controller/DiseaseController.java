package pl.tomo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.controller.exception.NoSaveFileException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.File;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.form.MedicamentForm;
import pl.tomo.entity.form.PatientForm;
import pl.tomo.provider.wrapper.DiseaseInfo;
import pl.tomo.provider.wrapper.FileBucket;
import pl.tomo.provider.wrapper.MedicamentsInDisease;
import pl.tomo.service.DiseaseMedicamentService;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.FileService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.PatientService;

@Controller
@RequestMapping(value = "/diseases")
@SessionAttributes({"patient", "list"})
public class DiseaseController {
		
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DiseaseMedicamentService diseaseMedicamentService;
	
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap, @RequestParam("list") String list) {
		ModelAndView modelAndView = new ModelAndView("diseases/diseases");
		PatientForm patientForm = diseaseService.getPatientForm(request);
		modelAndView.addObject("patientForm", patientForm);
		Patient patient = (Patient) modelMap.get("patient");
		if(patient != null) {
			List<Disease> diseases = diseaseService.findAllActive(patient, list);
			modelAndView.addObject("diseases", diseases);
			modelAndView.addObject("disease", new Disease());
			modelAndView.addObject("list", list);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public ModelAndView setSessionPatient(HttpServletRequest request, @ModelAttribute("patientForm") PatientForm patientForm) {
		ModelAndView mav = new ModelAndView();
		int id = patientForm.getId();
		Patient patient = patientService.getById(id);
		mav.addObject("patient", patient);
		mav.setViewName("redirect:/diseases" + Utills.makeUrlQueryByPrevious(request));
		return mav;
	}
	
	@RequestMapping(value = "/patient/change", method = RequestMethod.GET)
	public ModelAndView deleteSessionPatient(HttpServletRequest request, SessionStatus sessionStatus) {
		ModelAndView mav = new ModelAndView("redirect:/diseases");
		sessionStatus.setComplete();
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSubmit(HttpServletRequest request, @ModelAttribute("disease") Disease disease) {
		Patient patient = (Patient) request.getSession().getAttribute("patient");
		diseaseService.save(disease, patient);
		return new ModelAndView("redirect:/diseases");
	}
	
	
	@RequestMapping(value = "/archive/{id}", method = RequestMethod.GET)
	public ModelAndView archive(HttpServletRequest request, @PathVariable("id") int id, @RequestParam("date") long date) {
		ModelAndView modelAndView = new ModelAndView("redirect:/diseases");
		diseaseService.archive(id, date, request);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public ModelAndView getInfoTest(HttpServletRequest request, ModelMap modelMap, @PathVariable("id") int id) {
		Disease disease = diseaseService.findWithFilesUser(id);
		MedicamentsInDisease medicamentsInDisease = diseaseMedicamentService.getMedicamentsInDisease(request, disease);
		DiseaseInfo diseaseInfo = new DiseaseInfo(disease, medicamentsInDisease);
		ModelAndView modelAndView = new ModelAndView("diseases/info");
		modelAndView.addObject("diseaseInfo", diseaseInfo);
		modelAndView.addObject("medicamentForm", new MedicamentForm(id));
		modelAndView.addObject("fileBucket", new FileBucket());
		String object = (String) modelMap.get("list");
		return modelAndView;
	}
	
	@RequestMapping(value = "/dosages/{id}", method = RequestMethod.GET)
	public ModelAndView getDosages(HttpServletRequest request, @PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView("diseases/dosages");
		List<Dosage> dosages = diseaseService.findDosages(id, request);
		modelAndView.addObject("dosages", dosages);
		modelAndView.addObject("disease", diseaseService.findOne(id, request));
		return modelAndView;
	}
	
	@RequestMapping(value = "/medicaments/add", method = RequestMethod.POST)
	public ModelAndView addMedicamentsSubmit(HttpServletRequest request, @ModelAttribute("medicamentForm") MedicamentForm medicamentForm) {
		diseaseService.addMedicaments(medicamentForm, request);
		return new ModelAndView("redirect:" + Utills.makeUrlByPrevious(request));
		
	}
	
	@RequestMapping(value = "/medicaments/delete", method = RequestMethod.POST)
	public ModelAndView deleteMedicamentsSubmit(HttpServletRequest request, @ModelAttribute("medicamentForm") MedicamentForm medicamentForm) {
		diseaseService.deleteMedicaments(medicamentForm, request);
		return new ModelAndView("redirect:" + Utills.makeUrlByPrevious(request));
		
	}
	
	@RequestMapping(value="/{id}/upload", method = RequestMethod.POST)
	public ModelAndView processFormUpload(@PathVariable("id") int id, HttpServletRequest request, FileBucket fileBucket, HttpServletResponse httpServletResponse)
	{
		ModelAndView modelAndView = new ModelAndView("redirect:" + Utills.makeUrlByPrevious(request));
		try {
			fileService.save(fileBucket, request, id);
		} catch (IOException e1) {
			throw new  NoSaveFileException(request);
		}
		return modelAndView;
	}
	@RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") int id, HttpServletResponse response, HttpServletRequest request) {
		ServletContext context = request.getServletContext();
		try {
			File file = fileService.findById(id);
			String path = file.getPath();
			java.io.File downloadFile = new java.io.File(path);
			InputStream is = new FileInputStream(downloadFile);
			String mimeType = context.getMimeType(path);
			response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
      		String headerKey = "Content-Disposition";
      		String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);
            FileCopyUtils.copy(is, response.getOutputStream());
  			response.flushBuffer();
  			is.close();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}
	
	

}
