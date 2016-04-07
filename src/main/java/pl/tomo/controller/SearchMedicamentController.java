package pl.tomo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.tomo.medicament.entity.Medicament;
import pl.tomo.medicament.service.MedicamentMService;


@RestController
public class SearchMedicamentController {
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	
	
	@RequestMapping(value = "/medicament/medicaments-db", method = RequestMethod.GET, headers="Accept=application/json")
	 public List<Medicament> getMedicaments()
	 {
		return medicamentMService.findAll();
	 }

}
