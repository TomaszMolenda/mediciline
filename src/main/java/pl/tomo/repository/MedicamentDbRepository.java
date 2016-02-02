package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.MedicamentDb;

public interface MedicamentDbRepository extends JpaRepository<MedicamentDb, Integer>{
	
	List<MedicamentDb> findAll();
	
	MedicamentDb findOne(Integer id);
	
	

}
