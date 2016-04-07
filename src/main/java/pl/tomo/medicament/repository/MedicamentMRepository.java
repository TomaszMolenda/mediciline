package pl.tomo.medicament.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.Medicament;

@Repository
public interface MedicamentMRepository extends JpaRepository<Medicament, Integer>{

	@Query("SELECT m FROM Medicament m WHERE m.id = :id")
	Medicament getMedicamentById(@Param("id") int id);

	@Query("SELECT m.distributorID FROM Medicament m")
	Set<Integer> getDistributorsID();

	@Query("SELECT m.productLineID FROM Medicament m")
	Set<Integer> getProductLinesID();

	@Query("SELECT m.packageID FROM Medicament m")
	List<Integer> getPackageID();
	
	@Query("SELECT m.packageID FROM Medicament m WHERE m.active = true")
	List<Integer> getActivePackageID();

	@Query("SELECT m FROM Medicament m WHERE m.packageID = :packageID")
	Medicament getMedicamentByPackageID(@Param("packageID") Integer packageID);

	@Query("SELECT m FROM Medicament m WHERE m.packageID = :packageID AND m.active = :active")
	Medicament getMedicamentByPackageID(@Param("packageID") Integer packageID, @Param("active") boolean active);

}
