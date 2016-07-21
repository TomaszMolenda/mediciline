package pl.tomo.provider.wrapper;

import java.util.ArrayList;
import java.util.List;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;


public class MedicamentsInDisease {
		
	private User user;
	
	private List<Medicament> medicamentsInDisease;
	
	public MedicamentsInDisease() {
		
	}
	public MedicamentsInDisease(User user, List<Medicament> medicamentsInDisease) {
		this.user = user;
		this.medicamentsInDisease = medicamentsInDisease;
	}
	
	

	public List<Medicament> getMedicamentsToAdd() {
		List<Medicament> medicaments = new ArrayList<>();
		for (Medicament medicament : user.getMedicaments()) {
			if(!medicamentsInDisease.contains(medicament) & !medicament.isArchive())
				medicaments.add(medicament);
		}
		return medicaments;
	}

	public List<Medicament> getMedicamentsToDelete() {
		return medicamentsInDisease;
	}
	
	
	
	

}
