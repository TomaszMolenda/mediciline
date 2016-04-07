package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.MedicamentAdditional;

@Repository
public interface MedicamentAdditionalRepository extends JpaRepository<MedicamentAdditional, Integer>{
	
	@Query("SELECT m.productLineID FROM MedicamentAdditional m")
	List<Integer> getAllProductLinesId();



}
