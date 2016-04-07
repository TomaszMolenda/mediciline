package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.Distributor;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Integer>{

	@Query("SELECT d.distributorID FROM Distributor d")
	List<Integer> getAllId();



}
