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

import pl.tomo.entity.Disease;
import pl.tomo.entity.File;
import pl.tomo.entity.Medicament;



@Repository
public class FileRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	

	public List<File> findByDisease(Disease disease) {
		List resultList = entityManager.createNamedQuery("File.findByDisease")
				.setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("fileWithUserAndDiseases"))
				.setParameter("disease", disease)
				.getResultList();
		return resultList;
	}

}
