package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{

	List<Disease> findByUser(User user);
	
	@Query("select d from Disease d join fetch d.user where d.user.name = :name")
	List<Disease> findByUser(@Param("name") String name);
	
	@Query("select d from Disease d inner join d.medicaments m where d.user = :user")
	List<Disease> findByUserWithMedicaments(@Param("user") User user);
	
	@Query("select d from Disease d join fetch d.user join fetch d.medicaments where d.user.name = :name")
	List<Disease> findByUserWithMedicaments(@Param("name") String name);

	@Query("select d from Disease d join fetch d.user where d.id = :id")
	Disease findById(@Param("id") int diseaseId);

	@Query("select d, m from Disease d LEFT OUTER JOIN d.medicaments m")
	List<Object[]> findWithMedicaments();

	@Query("select d from Disease d join fetch d.user where d.id=:id")
	Disease findByIdWithUser(@Param("id") int id);

	@Query("SELECT d FROM Disease d join fetch d.patient where d.patient = :patient")
	List<Disease> findByPatient(@Param("patient") Patient patient);

	

	

	


}
