package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Medicament;



@Repository
public class MedicamentRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Medicament> getAll(String query, Map<String, Object> parametrs, String...param) {
		EntityGraph<Medicament> entityGraph = template(param);
		TypedQuery<Medicament> createQuery = entityManager.createQuery(query, Medicament.class);
		createQuery.setHint("javax.persistence.loadgraph", entityGraph);
		if(parametrs != null) {
			Set<String> keySet = parametrs.keySet();
			for (String string : keySet) {
				createQuery.setParameter(string, parametrs.get(string));
			}
		}
		
		List<Medicament> medicaments = createQuery.getResultList();
		return medicaments;
	}
	
	public Medicament getOne(String query, String...param) {
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
