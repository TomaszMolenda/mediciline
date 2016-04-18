package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.MedicamentAdditional;

@Repository
public interface MedicamentAdditionalRepository extends JpaRepository<MedicamentAdditional, Integer>{
	
	@Query("SELECT m.productLineID FROM MedicamentAdditional m")
	List<Integer> getAllProductLinesId();

	@Query("SELECT m FROM MedicamentAdditional m WHERE m.productLineID = :productLineID")
	MedicamentAdditional getById(@Param("productLineID") int productLineID);



}
