package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;

public interface MedicamentRepository extends JpaRepository<Medicament, Integer>{
	
	List<Medicament> findAll();

	
	List<Medicament> findByUser(User user);
	
	@Query("select m from Medicament m join fetch m.user u where m.user.name = :name")
	List<Medicament> findByUser(@Param("name") String name);

	@Query("select m from Medicament m where id = :id")
	Medicament findById(@Param("id") int id);
	
	@Query("select m from Medicament m join fetch m.user where m.id=:id")
	Medicament findByIdWithUser(@Param("id") int id);

	List<Medicament> findByDisease(Disease disease);
	
	@Modifying  
	@Transactional
	@Query("delete from Medicament m where m.id=:id")
	void delete(@Param("id") int id);

	@Query("select m from Medicament m join fetch m.disease d where d.id = :id")
	List<Medicament> findByDisease(@Param("id") int id);

	

	

	

	
	
	
	

}
