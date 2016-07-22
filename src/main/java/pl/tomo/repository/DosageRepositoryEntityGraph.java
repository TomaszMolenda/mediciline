package pl.tomo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Dosage;



@Repository
public class DosageRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;


	public Dosage findByIdWithUser(int id) {
		Dosage dosage = (Dosage) entityManager.createNamedQuery("Dosage.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("dosageWithUser"))
				.setParameter("id", id)
				.getSingleResult();
		return dosage;
	}

	public List<Dosage> findAllNotSended() {
		List<Dosage> resultList = entityManager.createNamedQuery("Dosage.findAddNotSended")
		.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("dosageWithUser")).getResultList();
		return resultList;
	}

}
