package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;



@Repository
public class MedicamentRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Medicament> getAll(String query, String...param) {
		EntityGraph<Medicament> entityGraph = template(param);
		List<Medicament> medicaments = entityManager.createQuery(query, Medicament.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return medicaments;
	}
	
	public Medicament getById(String query, String...param) {
		EntityGraph<Medicament> entityGraph = template(param);
		Medicament medicament = entityManager.createQuery(query, Medicament.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getSingleResult();
		return medicament;
	}

	private EntityGraph<Medicament> template(String... param) {
		EntityGraph<Medicament> entityGraph = entityManager.createEntityGraph(Medicament.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

}
