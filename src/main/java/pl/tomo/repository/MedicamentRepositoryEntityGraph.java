package pl.tomo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;



@Repository
public class MedicamentRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;


	public Medicament findById(int id) {
		Medicament medicament = (Medicament) entityManager.createNamedQuery("Medicament.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentWithUserAndDiseases"))
				.setParameter("id", id)
				.getSingleResult();
		return medicament;
	}
	
	public Medicament findByIdOnlyMedicament(int id) {
		Medicament medicament = (Medicament) entityManager.createNamedQuery("Medicament.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicament"))
				.setParameter("id", id)
				.getSingleResult();
		return medicament;
	}
	
	public Medicament findByIdWithUser(int id) {
		Medicament medicament = (Medicament) entityManager.createNamedQuery("Medicament.findById")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentWithUser"))
				.setParameter("id", id)
				.getSingleResult();
		return medicament;
	}

	public List<Medicament> findByUser(User user) {
		List medicaments = entityManager.createNamedQuery("Medicament.findAllByUser")
			.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentWithUserAndDiseases"))
			.setParameter("user", user)
			.getResultList();
		return medicaments;
	}

	public List<Medicament> findByArchiveAndUser(boolean b, User user) {
		List medicaments = entityManager.createNamedQuery("Medicament.findAllByArchiveAndUser")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentWithUserAndDiseases"))
				.setParameter("user", user)
				.setParameter("archive", b)
				.getResultList();
			return medicaments;
	}

}
