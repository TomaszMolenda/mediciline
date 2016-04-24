package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.Disease;

@Repository
public interface DiseaseMRepository extends JpaRepository<Disease, Integer>{

	@Query("SELECT d.diseaseID FROM Disease d")
	List<Integer> getAllId();

	@Query("SELECT d FROM Disease d WHERE d.diseaseID = :integer")
	Disease getActiveById(Integer integer);

	@Query("SELECT d FROM Disease d WHERE d.diseaseName = :disease")
	List<Disease> getByName(@Param("disease") String disease);

	@Query("SELECT max(d.diseaseID) FROM Disease d")
	int getMaxId();
}
