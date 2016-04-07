package pl.tomo.medicament.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.tomo.medicament.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>{

	@Query("select p.productTypeID from ProductType p")
	List<Integer> getAllId();
	@Query("select p from ProductType p WHERE p.active = true AND p.productTypeID = :integer")
	ProductType getActiveById(Integer integer);



}
