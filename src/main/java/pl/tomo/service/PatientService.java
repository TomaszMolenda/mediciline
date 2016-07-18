package pl.tomo.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import pl.tomo.entity.Patient;
import pl.tomo.repository.PatientRepository;
import pl.tomo.repository.PatientRepositoryEntityGraph;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PatientRepositoryEntityGraph patientRepositoryEntityGraph;

	public Patient save(Patient patient) throws ConstraintViolationException {
		long birthdayLong = patient.getBirthdayLong();
		patient.setBirthday(new Date(birthdayLong));
		int id = patient.getId();
		patient.setId(0);
		Patient savedPatient = patientRepository.save(patient);
		savedPatient.setIdServer(patient.getId());
		savedPatient.setId(id);
		return savedPatient;
	}

	public List<Patient> getAllByUser(String name) {
		return patientRepository.getAllByUser(name);
	}

	public Patient getById(int id) throws NoResultException{
		Patient patient = patientRepositoryEntityGraph.getById("select p from Patient p where p.id="+id, "user", "diseases");
		return patient;
	}

	public void delete(Patient patient) throws EmptyResultDataAccessException, MySQLIntegrityConstraintViolationException {
		try {
			patientRepository.delete(patient);
		} catch (Exception ex) {
			throw new MySQLIntegrityConstraintViolationException();
		}
		
		
	}

	public List<Patient> getAll() {
		return patientRepositoryEntityGraph.getAll();
	}

	

	


}
