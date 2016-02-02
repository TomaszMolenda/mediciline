package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.Disease;
import pl.tomo.entity.User;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{

	List<Disease> findByUser(User user);


}
