package pl.tomo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owlike.genson.Genson;

import pl.tomo.entity.Medicament;
import pl.tomo.service.MedicamentService;

@RestController
@RequestMapping(value = "/disease")
public class DiseaseControllerRest {
	
	@Autowired
	private MedicamentService medicamentService; 
	
	@RequestMapping(value = "/{id}/medicaments", produces = "application/json;charset=UTF-8")
	public String get(Principal principal, @PathVariable int id)
	{
		String name = principal.getName();
		List<Medicament> medicaments = medicamentService.findByDisease(id);
		//http://stackoverflow.com/questions/14708386/want-to-hide-some-fields-of-an-object-that-are-being-mapped-to-json-by-jackson
		//http://owlike.github.io/genson/GettingStarted/
		Genson genson = new Genson.Builder().exclude("user", Medicament.class).exclude("disease", Medicament.class).create();
		String json = genson.serialize(medicaments);
		return json;
	}
}
