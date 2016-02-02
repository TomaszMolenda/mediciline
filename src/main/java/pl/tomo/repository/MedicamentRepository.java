package pl.tomo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;

public interface MedicamentRepository extends JpaRepository<Medicament, Integer>{
	
	List<Medicament> findAll();

	List<Medicament> findByUser(User user);

	@Query("select m from Medicament m where id = :id")
	Medicament findById(@Param("id") int id);
	
	@Modifying
	@Transactional
	@Query("Update Medicament m set m.dateExpiration = :dateExpiration WHERE m.id = :id")
	void update(@Param("id") int id, @Param("dateExpiration") Date dateExpiration);
	
	
	
	

}
