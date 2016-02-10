package pl.tomo.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentDb;
import pl.tomo.entity.MedicamentForm;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseService;
import pl.tomo.service.MedicamentService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/disease")
public class DiseaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Principal principal)
	{
		ModelAndView mav = new ModelAndView("diseaseList");
		String name = principal.getName();
		User user = userService.findByName(name);
		List<Disease> diseases = diseaseService.findByUser(user);
		mav.addObject("diseases", diseases);
		return mav;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(Model model)
	{
		ModelAndView mav = new ModelAndView("diseaseAdd");
		mav.addObject("disease", new Disease());
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSubmit(Disease disease, Principal principal)
	{
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		String name = principal.getName();
		try {
			disease.setStart(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStartString()));
			disease.setStop(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStopString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = userService.findByName(name);
		disease.setUser(user);
		diseaseService.save(disease);
		return mav;
	}
	
	@RequestMapping(value = "/addmedicaments/{id}")
	public ModelAndView addMedicaments(@PathVariable int id, Principal principal)
	{
		ModelAndView mav = new ModelAndView("diseaseAddMedicaments");
		String name = principal.getName();
		User user = userService.findByName(name);
		MedicamentForm medicamentForm = new MedicamentForm();
		List<Medicament> list = medicamentService.findByUser(user);
		medicamentForm.setMedicaments(list);
		medicamentForm.setDiseaseId(id);
		mav.addObject("medicamentForm", medicamentForm);
		return mav;
		
	}
	
	@RequestMapping(value = "/addmedicaments/do")
	public ModelAndView addMedicamentsSubmit(@ModelAttribute("medicamentForm") MedicamentForm medicamentForm, Principal principal)
	{
		List<Integer> ids = medicamentForm.getIds();
		int diseaseId = medicamentForm.getDiseaseId();
		Disease disease = diseaseService.findById(diseaseId);
		List<Medicament> medicaments = medicamentService.findByDisease(disease);
		for (Integer id : ids) {
			Medicament medicament = medicamentService.findById(id);
			medicaments.add(medicament);
		}
		disease.setMedicaments(medicaments);
		diseaseService.save(disease);
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		
		
		
		
		
		
		return mav;
	}

	
	

}
