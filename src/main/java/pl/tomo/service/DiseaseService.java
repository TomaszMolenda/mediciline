package pl.tomo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.repository.DiseaseRepository;

@Service
public class DiseaseService {
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<Disease> findByUser(User user) {
		return diseaseRepository.findByUser(user);
	}

	public List<Disease> findByUser(String name) {
		return diseaseRepository.findByUser(name);
	}
	
	public List<Disease> findByUserWithMedicaments(User user) {
		return diseaseRepository.findByUserWithMedicaments(user);
	}
	
	public List<Disease> findByUserWithMedicaments(String name) {
		return diseaseRepository.findByUserWithMedicaments(name);
	}

	public void save(Disease disease, String userName, Patient patient) {
		User user = userService.findByName(userName);
		disease.setUser(user);
		disease.setPatient(patient);
		try 
		{
			disease.setStart(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStartString()));
			disease.setStop(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStopString()));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		diseaseRepository.save(disease);
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

	public List<Disease> findAll() {
		return diseaseRepository.findAll();
	}

	public List<Disease> findWithMedicaments() {
		List<Object[]> diseasesObjects = diseaseRepository.findWithMedicaments();
		Set<Disease> diseases = new HashSet<Disease>();
		for (Object objects[] : diseasesObjects) {
			Disease disease = (Disease) objects[0];
			diseases.add(disease);
			if(objects[1] != null)
			{
				Medicament medicament = (Medicament) objects[1];
				disease.addMedicament(medicament);
			}
		}	
		return new ArrayList<Disease>(diseases);
	
	}

	public void test(){
		Session openSession = sessionFactory.openSession();
		Query query = openSession.createQuery("Select d from Disease d");
		List<Disease> list = query.list();
		for (Disease disease : list) {
			Hibernate.initialize(disease.getMedicaments());
		}
		openSession.close();

	}

	public Disease findByIdWithUser(int id) {
		return diseaseRepository.findByIdWithUser(id);
	}

	public List<Disease> findByPatient(Patient patient) {
		return diseaseRepository.findByPatient(patient);
	}


	
	


}
