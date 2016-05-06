package pl.tomo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Patient;
import pl.tomo.repository.PatientRepository;

@Service
public class PatientService {
	
	private Logger logger = Logger.getLogger(PatientService.class);

	@Autowired
	private PatientRepository patientRepository;

	public void save(Patient patient) {
		patientRepository.save(patient);
		logger.info("save patient, id: " + patient.getId());
	}

	public List<Patient> getAllByUser(String name) {
		logger.info("get list patients, by user: " + name);
		return patientRepository.getAllByUser(name);
	}

	public Patient getById(int id) {
		logger.info("get patient, id: " + id);
		return patientRepository.getById(id);
	}

	


}
