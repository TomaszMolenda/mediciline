package pl.tomo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.MedicamentForm;
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
		ModelAndView mav = new ModelAndView("diseases");
		String name = principal.getName();
		List<Disease> diseases = diseaseService.findByUser(name);
		mav.addObject("diseases", diseases);
		mav.addObject("disease", new Disease());
		MedicamentForm medicamentForm = new MedicamentForm();
		List<Medicament> medicaments = medicamentService.findByUser(name);
		medicamentForm.setMedicaments(medicaments);
		mav.addObject("medicamentForm", medicamentForm);
		mav.addObject("medicamentRemoveForm", new MedicamentForm());
		return mav;
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView addSubmit(@ModelAttribute("disease") Disease disease, Principal principal)
	{
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		String name = principal.getName();
		diseaseService.save(disease, name);
		return mav;
	}
	
	@RequestMapping(value = "/addMedicaments")
	public ModelAndView addMedicamentsSubmit(@ModelAttribute("medicamentForm") MedicamentForm medicamentForm, Principal principal)
	{
		List<Integer> ids = medicamentForm.getIds();
		int diseaseId = medicamentForm.getDiseaseId();
		Disease disease = diseaseService.findById(diseaseId);
		List<Medicament> medicaments = medicamentService.findByDisease(disease);
		List<Integer> idMedicaments = new ArrayList<Integer>();
		for (Medicament medicament : medicaments) {
			idMedicaments.add(medicament.getId());
		}
		for (Integer idMedicament : ids) {

			if(!idMedicaments.contains(idMedicament))
			{
				Medicament medicament = medicamentService.findById(idMedicament);
				medicaments.add(medicament);
			}

		}
		disease.setMedicaments(medicaments);
		
		diseaseService.save(disease);
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		return mav;
	}

	@RequestMapping(value = "/remove/{id}")
	public ModelAndView remove(@PathVariable int id, Principal principal)
	{
		String name = principal.getName();

		Disease disease = diseaseService.findByIdWithUser(id);
		if(disease.getUser().getName().equals(name))
		{
			diseaseService.delete(id);
			return new ModelAndView("redirect:/disease/list.html");
		}

		return new ModelAndView("redirect:/no-access.html");
	}
	
	@RequestMapping(value = "/removeMedicaments")
	public ModelAndView removeMedicamentsSubmit(@ModelAttribute("medicamentRemoveForm") MedicamentForm medicamentForm, Principal principal)
	{
		List<Integer> ids = medicamentForm.getIds();
		System.out.println(ids.toString());
		int diseaseId = medicamentForm.getDiseaseId();
		System.out.println(diseaseId);
		Disease disease = diseaseService.findById(diseaseId);
		System.out.println(disease.toString());
		List<Medicament> medicaments = medicamentService.findByDisease(disease);
		for (Integer integer : ids) {
			System.out.println(integer);
			for (Medicament medicament : medicaments) {
				System.out.println(medicament.toString());
				if(integer.equals(medicament.getId())) {
					medicaments.remove(medicament);
					break;
				}
			}
		}
		disease.setMedicaments(medicaments);
		diseaseService.save(disease);
		ModelAndView mav = new ModelAndView("redirect:/disease/list.html");
		return mav;
	}

	

}
