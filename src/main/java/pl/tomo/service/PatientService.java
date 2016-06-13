package pl.tomo.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import pl.tomo.entity.Patient;
import pl.tomo.repository.PatientRepository;
import pl.tomo.repository.PatientRepositoryEntityGraph;

@Service
public class PatientService {
	
	private Logger logger = Logger.getLogger(PatientService.class);

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PatientRepositoryEntityGraph patientRepositoryEntityGraph;

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

	public Patient getById(int id) throws NoResultException{
		Patient patient = patientRepositoryEntityGraph.getById("select p from Patient p where p.id="+id, "user", "diseases");
		logger.info("get patient, id: " + id);
		return patient;
	}

	public void delete(Patient patient) throws EmptyResultDataAccessException, MySQLIntegrityConstraintViolationException {
		try {
			patientRepository.delete(patient);
			logger.info("delete patient, id: " + patient.getId());
		} catch (Exception ex) {
			throw new MySQLIntegrityConstraintViolationException();
		}
		
		
	}

	


}
