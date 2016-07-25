package pl.tomo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.Disease;
import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;



@Repository
public class DiseaseMedicamentRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;


	public List<Disease> findByMedicament(Medicament medicament) {
		List<DiseaseMedicament> diseaseMedicaments  = entityManager.createNamedQuery("DiseaseMedicament.findByMedicament")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentsAndDisease"))
				.setParameter("medicament", medicament)
				.getResultList();
		List<Disease> diseases = new ArrayList<>();
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			Disease disease = diseaseMedicament.getDisease();
			diseases.add(disease);
		}
		return diseases;
	}
	
	public List<Medicament> findByMedicamentsId(List<Integer> integers) {
		List<DiseaseMedicament> diseaseMedicaments  = entityManager.createNamedQuery("DiseaseMedicament.findByMedicamentsId")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentsAndDisease"))
				.setParameter("ids", integers)
				.getResultList();
		System.out.println(diseaseMedicaments);
		List<Medicament> medicaments = new ArrayList<>();
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			Medicament medicament = diseaseMedicament.getMedicament();
			medicaments.add(medicament);
		}
		return medicaments;
	}
	
	public List<Medicament> findWithDisease(Disease disease) {
		List<DiseaseMedicament> diseaseMedicaments  = entityManager.createNamedQuery("DiseaseMedicament.findByDisease")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicaments"))
				.setParameter("disease", disease)
				.getResultList();
		List<Medicament> medicaments = new ArrayList<>();
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			Medicament medicament = diseaseMedicament.getMedicament();
			medicaments.add(medicament);
		}
		return medicaments;
	}
	
	public void save(Medicament medicament, Disease disease) {
		DiseaseMedicament diseaseMedicament = new DiseaseMedicament();
		diseaseMedicament.setDisease(disease);
		diseaseMedicament.setMedicament(medicament);
		entityManager.persist(diseaseMedicament);
	}

	public DiseaseMedicament findOne(Medicament medicament, Disease disease) {
		DiseaseMedicament diseaseMedicament = (DiseaseMedicament) entityManager.createNamedQuery("DiseaseMedicament.findByDiseaseAndMedicament")
		.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("medicamentsAndDisease"))
		.setParameter("disease", disease)
		.setParameter("medicament", medicament)
		.getSingleResult();
		return diseaseMedicament;
	}
	
	public DiseaseMedicament findOneWithDosages(Medicament medicament, Disease disease) {
		DiseaseMedicament diseaseMedicament = (DiseaseMedicament) entityManager.createNamedQuery("DiseaseMedicament.findByDiseaseAndMedicament")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("dosages"))
				.setParameter("disease", disease)
				.setParameter("medicament", medicament)
				.getSingleResult();
				return diseaseMedicament;
	}

	public List<DiseaseMedicament> findWithDisease(Medicament medicament) {
		List<DiseaseMedicament> diseaseMedicaments  = entityManager.createNamedQuery("DiseaseMedicament.findByMedicament")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("diseases"))
				.setParameter("medicament", medicament)
				.getResultList();
		List<Medicament> medicaments = new ArrayList<>();
		return diseaseMedicaments;
	}

	
}
