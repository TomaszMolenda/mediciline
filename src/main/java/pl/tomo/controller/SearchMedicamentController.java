package pl.tomo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.tomo.entity.MedicamentDb;
import pl.tomo.service.MedicamentDbService;

@RestController
public class SearchMedicamentController {
	
	@Autowired
	private MedicamentDbService medicamentDbService;
	
	@RequestMapping(value = "/medicaments-db", method = RequestMethod.GET, headers="Accept=application/json")
	 public List<MedicamentDb> getMedicaments()
	 {
		return medicamentDbService.findAll();
	 }

}
