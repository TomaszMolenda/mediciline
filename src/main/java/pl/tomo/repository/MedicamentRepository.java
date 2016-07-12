package pl.tomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcabi.aspects.Loggable;

import pl.tomo.entity.Medicament;

@Loggable
public interface MedicamentRepository extends JpaRepository<Medicament, Integer>{

}
