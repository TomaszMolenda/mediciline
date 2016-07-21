package pl.tomo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
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
	
	
	
	
	
}
