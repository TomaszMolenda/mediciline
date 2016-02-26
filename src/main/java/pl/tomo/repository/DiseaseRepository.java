package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Disease;
import pl.tomo.entity.User;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{

	List<Disease> findByUser(User user);
	
	@Query("select d from Disease d inner join d.medicaments m where d.user = :user")
	List<Disease> findByUserWithMedicaments(@Param("user") User user);

	Disease findById(int diseaseId);

	


}
