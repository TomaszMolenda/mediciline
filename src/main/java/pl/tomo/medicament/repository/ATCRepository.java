package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.ATC;

@Repository
public interface ATCRepository extends JpaRepository<ATC, Integer>{

	@Query("SELECT a.productLineID from ATC a")
	List<Integer> getAllProductLinesId();

	@Query("SELECT a from ATC a where a.productLineID = :productLineID")
	List<ATC> getATCs(@Param("productLineID") int productLineID);



}
