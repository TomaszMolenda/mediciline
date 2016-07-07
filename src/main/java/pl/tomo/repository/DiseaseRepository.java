package pl.tomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{
	


}
