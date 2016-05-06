package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.repository.MedicamentAdditionalRepository;

@Service
public class MedicamentAdditionalService {
	
	private Logger logger = Logger.getLogger(MedicamentAdditionalService.class);
	
	@Autowired
	private MedicamentAdditionalRepository medicamentAdditionalRepository;
	
	public void save(MedicamentAdditional medicamentAdditional) {
		medicamentAdditionalRepository.save(medicamentAdditional);
		logger.info("save medicamentAdditional, productline id: " + medicamentAdditional.getProductLineID());
	}

	public void save(List<MedicamentAdditional> medicamentAdditionals) {
		for (MedicamentAdditional medicamentAdditional : medicamentAdditionals) {
			medicamentAdditionalRepository.save(medicamentAdditional);
			logger.info("save medicamentAdditional, productline id: " + medicamentAdditional.getProductLineID());
		}
		
	}

	public List<Integer> getAllProductLinesId() {
		logger.info("get list productlines id");
		return medicamentAdditionalRepository.getAllProductLinesId();
	}

	public MedicamentAdditional getById(int productLineID) {
		logger.info("get MedicamentAdditional by productlines id: " + productLineID);
		return medicamentAdditionalRepository.getById(productLineID);
	}

	
}
