package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.repository.MedicamentAdditionalRepository;

@Service
public class MedicamentAdditionalService {
	
	@Autowired
	private MedicamentAdditionalRepository medicamentAdditionalRepository;
	
	public void save(MedicamentAdditional medicamentAdditional) {
		medicamentAdditionalRepository.save(medicamentAdditional);
	}

	public void save(List<MedicamentAdditional> medicamentAdditionals) {
		for (MedicamentAdditional medicamentAdditional : medicamentAdditionals) {
			medicamentAdditionalRepository.save(medicamentAdditional);
		}
		
	}

	public List<Integer> getAllProductLinesId() {
		return medicamentAdditionalRepository.getAllProductLinesId();
	}

	public MedicamentAdditional getById(int productLineID) {
		return medicamentAdditionalRepository.getById(productLineID);
	}

	
}
