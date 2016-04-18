package pl.tomo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Patient;
import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.repository.PatientRepository;
import pl.tomo.repository.RoleRepository;
import pl.tomo.repository.UserRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	
	public void save(Patient patient) {
		patientRepository.save(patient);
	}


	public List<Patient> getAllByUser(String name) {
		return patientRepository.getAllByUser(name);
		
	}


	public Patient getById(int id) {
		return patientRepository.getById(id);
	}


	public void delete(Patient patient) {
		patientRepository.delete(patient);
		
	}

	


}
