package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Disease;



@Repository
public class DiseaseRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
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

}
