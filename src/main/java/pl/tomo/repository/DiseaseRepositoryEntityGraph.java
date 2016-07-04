package pl.tomo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;



@Repository
public class DiseaseRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Disease> getAll(Patient patient) {
		List resultList = entityManager.createNamedQuery("Disease.findAllByPatient")
			.setParameter("patient", patient)
			.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("diseaseAndUserWithMedicaments"))
			.getResultList();
		return new ArrayList<Disease>(new LinkedHashSet<Disease>(resultList));
	}
	
	public List<Disease> getAll(Patient patient, boolean b) {
		List resultList = entityManager.createNamedQuery("Disease.findAllByPatientAndActive")
				.setParameter("patient", patient)
				.setParameter("archive", b)
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("diseaseAndUserWithMedicaments"))
				.getResultList();
			return new ArrayList<Disease>(new LinkedHashSet<Disease>(resultList));
	}
	
	public List<Disease> getAll(String query, String...param) {
		EntityGraph<Disease> entityGraph = template(param);
		List<Disease> diseases = entityManager.createQuery(query, Disease.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return diseases;
	}
	
	public Disease getById(String query, String...param) {
		EntityGraph<Disease> entityGraph = template(param);
		Disease disease = entityManager.createQuery(query, Disease.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getSingleResult();
		return disease;
	}
	
	private EntityGraph<Disease> template(String... param) {
		EntityGraph<Disease> entityGraph = entityManager.createEntityGraph(Disease.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

	public Disease finById(int id) {
		Disease disease = (Disease) entityManager.createNamedQuery("Disease.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("diseaseAndUserWithMedicaments"))
				.setParameter("id", id)
				.getSingleResult();
			
		return disease;
	}

	

	

}
