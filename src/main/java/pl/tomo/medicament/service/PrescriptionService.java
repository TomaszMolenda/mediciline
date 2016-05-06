package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Prescription;
import pl.tomo.medicament.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	private Logger logger = Logger.getLogger(PrescriptionService.class);
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	public void save(Prescription prescription) {
		prescriptionRepository.save(prescription);
		logger.info("save prescription, id " + prescription.getPrescriptionID());
	}

	public List<Integer> getAllId() {
		logger.info("get list prescriptions");
		return prescriptionRepository.getAllId();
	}

	public Prescription getPrescription(int prescriptionID) {
		logger.info("get prescription, id: " + prescriptionID);
		return prescriptionRepository.getOne(prescriptionID);
	}

}
