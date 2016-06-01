package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Dosage;

public interface DosageRepository extends JpaRepository<Dosage, Integer>{

	@Query("select d from Dosage d where d.idMD = :idMD")
	List<Dosage> getDosages(@Param("idMD") int idMD);

}
