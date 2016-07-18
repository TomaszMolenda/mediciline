package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Patient;



@Repository
public class PatientRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Patient> getAll(String query, String...param) {
		EntityGraph<Patient> entityGraph = template(param);
		List<Patient> patients = entityManager.createQuery(query, Patient.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return patients;
	}
	
	public Patient getById(String query, String...param) throws NoResultException{
		EntityGraph<Patient> entityGraph = template(param);
		Patient patient = entityManager.createQuery(query, Patient.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getSingleResult();
		return patient;
	}

	private EntityGraph<Patient> template(String... param) {
		EntityGraph<Patient> entityGraph = entityManager.createEntityGraph(Patient.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

	public List<Patient> getAll() {
		return entityManager.createNamedQuery("Patient.findAll")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("patient"))
				.getResultList();
	}

}
