package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{

	List<Disease> findByUser(User user);

	@Query("select d from Disease d join fetch d.user where d.id = :id")
	Disease findById(@Param("id") int diseaseId);

	@Query("select d from Disease d join fetch d.user where d.id=:id")
	Disease findByIdWithUser(@Param("id") int id);

	@Query("SELECT d FROM Disease d join fetch d.patient where d.patient = :patient")
	List<Disease> findByPatient(@Param("patient") Patient patient);

	

	

	


}
