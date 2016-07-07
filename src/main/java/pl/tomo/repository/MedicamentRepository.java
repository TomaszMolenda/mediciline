package pl.tomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Integer>{

}
