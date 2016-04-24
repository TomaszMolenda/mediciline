package pl.tomo.medicament.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.User;
import pl.tomo.medicament.entity.Medicament;



@Repository
public class MedicamentMRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager2;
	
	public List<Medicament> getAll(String query, String...param) {
		EntityGraph<Medicament> entityGraph = template(param);
		List<Medicament> medicaments = entityManager2.createQuery(query, Medicament.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return medicaments;
	}

	private EntityGraph<Medicament> template(String... param) {
		EntityGraph<Medicament> entityGraph = entityManager2.createEntityGraph(Medicament.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

	public Medicament getMedicamentByPackageID(Integer packageID) {
		EntityGraph<Medicament> entityGraph = template("medicamentAdditional"
				,"atcs"
				,"distributor"
				,"productType"
				,"prescription"
				,"diseases");
		Medicament medicament = entityManager2.createQuery("SELECT m from Medicament m WHERE m.packageID = " + packageID, Medicament.class)
				.setHint("javax.persistence.loadgraph", entityGraph).getSingleResult();
		return medicament;
	}

}
