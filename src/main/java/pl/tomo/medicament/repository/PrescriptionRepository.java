package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{

	@Query("select p.prescriptionID from Prescription p")
	List<Integer> getAllId();
	@Query("select p from Prescription p WHERE p.active = true AND p.prescriptionID = :integer")
	Prescription getActiveById(Integer integer);



}
