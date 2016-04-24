package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Disease;
import pl.tomo.medicament.repository.DiseaseMRepository;

@Service
public class DiseaseMService {
	
	@Autowired
	private DiseaseMRepository diseaseMRepository;
	
	public void save(Disease disease) {
		diseaseMRepository.save(disease);
	}
	
	public void save(List<Disease> diseases) {
		for (Disease disease : diseases) {
			diseaseMRepository.save(disease);
		}
	}

	public List<Integer> getAllId() {
		return diseaseMRepository.getAllId();
	}

	public Disease getActiveById(Integer integer) {
		return diseaseMRepository.getActiveById(integer);
	}

	public List<Disease> getByName(String disease) {
		return diseaseMRepository.getByName(disease);
	}

	public int getMaxId() {
		return diseaseMRepository.getMaxId();
	}

}
