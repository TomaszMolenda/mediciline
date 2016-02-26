package pl.tomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Disease;
import pl.tomo.entity.User;
import pl.tomo.repository.DiseaseRepository;

@Service
public class DiseaseService {
	
	@Autowired
	private DiseaseRepository diseaseRepository;

	public List<Disease> findByUser(User user) {
		return diseaseRepository.findByUser(user);
	}
	
	public List<Disease> findByUserWithMedicaments(User user) {
		return diseaseRepository.findByUserWithMedicaments(user);
	}

	public void save(Disease disease) {
		diseaseRepository.save(disease);
		
	}

	public Disease findById(int diseaseId) {
		
		return diseaseRepository.findById(diseaseId);
	}

	public void delete(int id) {
		diseaseRepository.delete(id);
		
	}
	
	


}
