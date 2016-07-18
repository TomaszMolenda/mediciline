package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;



@Repository
public class DosageRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Dosage> getAll(String query, String...param) {
		EntityGraph<Dosage> entityGraph = template(param);
		List<Dosage> dosages = entityManager.createQuery(query, Dosage.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return dosages;
	}
	
	public Dosage getById(String query, String...param) {
		EntityGraph<Dosage> entityGraph = template(param);
		Dosage dosage = entityManager.createQuery(query, Dosage.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getSingleResult();
		return dosage;
	}

	private EntityGraph<Dosage> template(String... param) {
		EntityGraph<Dosage> entityGraph = entityManager.createEntityGraph(Dosage.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

	public Dosage findByIdWithUser(int id) {
		Dosage dosage = (Dosage) entityManager.createNamedQuery("Dosage.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("dosageWithUser"))
				.setParameter("id", id)
				.getSingleResult();
		return dosage;
	}

}
