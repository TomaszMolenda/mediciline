package pl.tomo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import pl.tomo.entity.Medicament;

public interface TestRepository extends PagingAndSortingRepository<Medicament, Integer>{
	
	Page<Medicament> findAll(Pageable pageRequest);
}
