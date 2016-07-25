package pl.tomo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.provider.wrapper.MedicamentsInDisease;
import pl.tomo.repository.DiseaseMedicamentRepositoryEntityGraph;

@Service
public class DiseaseMedicamentService {
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private DiseaseMedicamentRepositoryEntityGraph diseaseMedicamentRepositoryEntityGraph;

	public MedicamentsInDisease getMedicamentsInDisease(HttpServletRequest request, Disease disease) {
		User user = userService.findWithMedicaments(request);
		if(!disease.getUser().equals(user))
			throw new AccessDeniedException();
		List<Medicament> medicaments = diseaseMedicamentRepositoryEntityGraph.findWithDisease(disease);
		
		return new MedicamentsInDisease(user, medicaments);
	}

	public Set<Dosage> find(Disease disease, Medicament medicament) {
		DiseaseMedicament diseaseMedicament = diseaseMedicamentRepositoryEntityGraph.findOneWithDosages(medicament, disease);
		return diseaseMedicament.getDosages();
	}

	public DiseaseMedicament finOne(Disease disease, Medicament medicament) {
		return diseaseMedicamentRepositoryEntityGraph.findOneWithDosages(medicament, disease);
	}

	public List<Dosage> findDosages(Disease disease) {
		Set<Dosage> dosages = new HashSet<Dosage>();
		List<DiseaseMedicament> diseaseMedicaments = diseaseMedicamentRepositoryEntityGraph.findWithDosagesAndMedicament(disease);
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			dosages.addAll(diseaseMedicament.getDosages());
		}
		List<Dosage> returnDosages = new ArrayList<Dosage>(dosages);
		Collections.sort(returnDosages);
		return returnDosages;
		
	}

	
	
}
