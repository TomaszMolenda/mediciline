package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Patient;
import pl.tomo.entity.User;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	@Query("select p from Patient p join fetch p.user u where p.user.name = :name")
	List<Patient> getAllByUser(@Param("name") String name);

	@Query("select p from Patient p join fetch p.user u where p.id = :id")
	Patient getById(@Param("id") int id);

	
}
