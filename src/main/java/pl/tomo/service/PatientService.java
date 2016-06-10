package pl.tomo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

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

	public void save(Patient patient) throws ConstraintViolationException{
		long birthdayLong = patient.getBirthdayLong();
		patient.setBirthday(new Date(birthdayLong));
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

	public void delete(int id) {
		patientRepository.delete(id);
		logger.info("delete patient, id: " + id);
	}

	


}
